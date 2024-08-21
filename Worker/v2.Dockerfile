FROM python:3.11-alpine

WORKDIR worker

COPY requirements.txt requirements.txt
COPY requirements.v2.txt requirements.v2.txt
COPY app app
COPY worker_v2.py worker_v2.py

RUN pip install -r requirements.v2.txt

ENTRYPOINT ["python", "worker_v2.py"]