<h2>TextSummarizeX</h2>

<h3>How to run the FastAPI app as prod, locally using uvicorn?</h3>

1. Clone the Repo

2. Create and run in virtual env

```bash
apt install python3.7
apt install python3.7-venv
cd TextSummarizeX/backend
python3.7 -m venv ./.venv
source ./.venv/bin/activate
```

3. Install the requirements

```bash
pip install -r requirements.txt
```

4. Run the FastAPI backend server

```bash
uvicorn api:app --host 0.0.0.0 --port 8000 --timeout-keep-alive 20000
```

