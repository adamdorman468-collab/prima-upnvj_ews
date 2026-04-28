"""Shared prediction logic for FastAPI + Streamlit.

This module is safe to import even if the model file is missing.
Call `predict_student_status(...)` to run inference.
"""

from __future__ import annotations

from dataclasses import dataclass
from pathlib import Path
from typing import Any, Tuple

import joblib
import pandas as pd


MODEL_FILENAME = "model_ews_rf.joblib"


@dataclass(frozen=True)
class ModelLoadState:
    model: Any | None
    error: str | None


_STATE: ModelLoadState | None = None


def _load_model_safely(model_path: str | Path = MODEL_FILENAME) -> ModelLoadState:
    path = Path(model_path)
    try:
        model = joblib.load(str(path))
        return ModelLoadState(model=model, error=None)
    except FileNotFoundError:
        return ModelLoadState(model=None, error=f"Model file not found: {path}")
    except Exception as e:  # noqa: BLE001
        return ModelLoadState(model=None, error=f"Failed to load model: {e}")


def get_model_state() -> ModelLoadState:
    """Returns cached model load state (loads once on first call)."""

    global _STATE
    if _STATE is None:
        _STATE = _load_model_safely()
    return _STATE


def _build_input_dataframe(
    *,
    ips1: float,
    ips2: float,
    ips3: float,
    ips4: float,
    sks: int,
    jk: int,
) -> pd.DataFrame:
    selisih_ips = ips4 - ips1
    return pd.DataFrame(
        {
            "IPS_1": [ips1],
            "IPS_2": [ips2],
            "IPS_3": [ips3],
            "IPS_4": [ips4],
            "Total_SKS_Smt4": [sks],
            "Selisih_IPS": [selisih_ips],
            "Jenis Kelamin": [jk],
        }
    )


def predict_student_status(
    *,
    ips1: float,
    ips2: float,
    ips3: float,
    ips4: float,
    sks: int,
    jk: int,
) -> Tuple[int, Any]:
    """Run model inference.

    Returns:
        (prediction, probabilities)

    Raises:
        FileNotFoundError: if the model file is missing
        RuntimeError: if the model failed to load for another reason
        Exception: any underlying prediction error
    """

    state = get_model_state()
    if state.model is None:
        if state.error and state.error.startswith("Model file not found"):
            raise FileNotFoundError(state.error)
        raise RuntimeError(state.error or "Model is not available")

    df_input = _build_input_dataframe(
        ips1=ips1,
        ips2=ips2,
        ips3=ips3,
        ips4=ips4,
        sks=sks,
        jk=jk,
    )

    pred = int(state.model.predict(df_input)[0])
    proba = state.model.predict_proba(df_input)[0]
    return pred, proba
