# Prima EWS FastAPI Backend

Run the local prediction API from PowerShell:

```powershell
.\run.ps1
```

The script creates `backend/.venv` if needed, installs `requirements.txt`, and starts:

```powershell
uvicorn main:app --host 0.0.0.0 --port 5000
```

Health check:

```text
http://127.0.0.1:5000/
```

Android emulator note: the app uses `http://10.0.2.2:5000/` so the emulator can reach this host machine. Keep FastAPI running while testing the Warning tab prediction form.

If Windows has reserved port `5000`, use another port for backend checks:

```powershell
.\run.ps1 -Port 5001
```

For emulator testing, keep the Android `BASE_URL` port aligned with the backend port.

Sample request:

```powershell
Invoke-RestMethod -Uri http://127.0.0.1:5000/predict `
  -Method Post `
  -ContentType "application/json" `
  -Body '{"ips1":3.42,"ips2":3.31,"ips3":3.18,"ips4":3.08,"sks":84,"jk":1}'
```
