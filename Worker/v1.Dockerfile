FROM python:3.11-alpine

WORKDIR worker

COPY requirements.txt requirements.txt
COPY requirements.v1.txt requirements.v1.txt
COPY app app
COPY worker.py worker.py

RUN pip install -r requirements.v1.txt

ENTRYPOINT ["python", "worker.py"]