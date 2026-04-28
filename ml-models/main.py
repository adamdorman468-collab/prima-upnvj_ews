from fastapi import FastAPI, HTTPException
from pydantic import BaseModel, Field

from predictor import predict_student_status

# Inisialisasi Aplikasi
app = FastAPI(
    title="API EWS Kelulusan FIK UPNVJ",
    description="REST API untuk memprediksi potensi DO mahasiswa menggunakan Random Forest",
    version="1.0.0"
)

# 1. Definisi Data Masuk (Request dari Android)
class StudentData(BaseModel):
    ips1: float = Field(ge=0.0, le=4.0)
    ips2: float = Field(ge=0.0, le=4.0)
    ips3: float = Field(ge=0.0, le=4.0)
    ips4: float = Field(ge=0.0, le=4.0)
    sks: int = Field(ge=0)
    jk: int = Field(ge=0, le=1)  # 1: Laki-laki, 0: Perempuan

# 2. Endpoint Prediksi
@app.post("/predict")
async def predict_status(data: StudentData):
    try:
        prediksi, probabilitas = predict_student_status(
            ips1=data.ips1,
            ips2=data.ips2,
            ips3=data.ips3,
            ips4=data.ips4,
            sks=data.sks,
            jk=data.jk,
        )

        # Return ke Android (JSON otomatis)
        return {
            "status": "success",
            "prediction": prediksi,
            "confidence": float(probabilitas[prediksi] * 100),
            "message": "Analisis FastAPI berhasil dilakukan"
        }
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))

# Root endpoint untuk cek status server
@app.get("/")
async def root():
    return {"message": "Server FastAPI EWS UPNVJ Berjalan Normal 🚀"}
