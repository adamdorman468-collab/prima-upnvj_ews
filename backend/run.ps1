param(
    [int]$Port = 5000
)

$ErrorActionPreference = "Stop"

$BackendDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$VenvDir = Join-Path $BackendDir ".venv"
$PythonExe = Join-Path $VenvDir "Scripts\python.exe"

Set-Location $BackendDir

if (-not (Test-Path $PythonExe)) {
    python -m venv $VenvDir
}

& $PythonExe -m pip install -r (Join-Path $BackendDir "requirements.txt")
& $PythonExe -m uvicorn main:app --host 0.0.0.0 --port $Port
