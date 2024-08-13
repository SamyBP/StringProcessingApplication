FROM python:3.11-alpine


COPY requirements.txt requirements.txt
COPY main.py main.py


RUN pip install -r requirements.txt


ENTRYPOINT ["fastapi", "run", "main.py", "--port", "8000", "--host", "0.0.0.0"]