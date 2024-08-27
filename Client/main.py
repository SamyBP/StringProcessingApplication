
from fastapi import FastAPI
from pydantic import BaseModel

from typing import List
from dotenv import load_dotenv

import requests
import os

load_dotenv()

STRING_PROCESSING_ENDPOINT_APP = os.getenv("STRING_PROCESSING_ENDPOINT_APP")

app = FastAPI()


class ProcessedItem(BaseModel):
    id: int
    result: str
    isSuccess: bool
    startedAt: int
    endedAt: int


class Module(BaseModel):
    name: str
    args: dict


class TriggerOperation(BaseModel):
    id: int
    input: str
    callback: str
    version: int
    modules: List[Module]


@app.post("/callback")
def callback_success(item: ProcessedItem):
    print(f"Received {item=}")
    return {"status": "OK"}


@app.post("/trigger_execution")
def trigger_execution(item: TriggerOperation):

    print(f"Making a post request to {STRING_PROCESSING_ENDPOINT_APP} with the following payload: {item.dict()}")

    r = requests.post(STRING_PROCESSING_ENDPOINT_APP, json=item.dict())

    print(f"Received response status {r.status_code}")
    print(r.text)
    return {"status": "OK"}


@app.post("/test_handle_execution")
def test_handle_execution(item: TriggerOperation):
    print(f"Received the following item to process {item=}")

    return {"status": "OK"}