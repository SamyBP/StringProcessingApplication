FROM python:3.11-alpine

WORKDIR worker

COPY requirements.txt requirements.txt
COPY app app
COPY worker.py worker.py

RUN pip install -r requirements.txt

ENTRYPOINT ["python", "worker.py"]