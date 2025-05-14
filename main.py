from fastapi import FastAPI, WebSocket, Request
from fastapi.websockets import WebSocketDisconnect
from typing import List
import asyncio
from fastapi.middleware.cors import CORSMiddleware
from dice_core import process_attack_data, process_attack_dumb_data

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

connected_clients: List[WebSocket] = []

@app.websocket("/ws")
async def websocket_endpoint(websocket: WebSocket):
    await websocket.accept()
    connected_clients.append(websocket)
    try:
        while True:
            await asyncio.sleep(0.1)
    except WebSocketDisconnect:
        connected_clients.remove(websocket)

@app.post("/calculation_dumb")
async def calculate_dumb(request: Request):
    data = await request.json()
    result = process_attack_dumb_data(data)
    return result

@app.post("/calculation_advanced")
async def calculate_dumb(request: Request):
    data = await request.json()
    res = process_attack_data(data)
    result = res
    return result

@app.post("/send_command")
async def send_command():
    command = {"action": "add", "value": "3d6"}
    for client in connected_clients:
        await client.send_json(command)
    return {"message": "Команда отправлена"}

@app.post("/delete")
async def delete_dice(index: int):
    command = {"action": "delete", "index": index}
    for client in connected_clients:
        await client.send_json(command)
    return {"message": f"Куб с index {index} удалён"}

@app.post("/update")
async def update_dice(index: int, value: str):
    command = {"action": "update", "index": index, "value": value}
    for client in connected_clients:
        await client.send_json(command)
    return {"message": f"Куб с index {index} обновлён на {value}"}

@app.post("/clear")
async def clear_dice():
    command = {"action": "clear"}
    for client in connected_clients:
        await client.send_json(command)
    return {"message": "Все кубы очищены"}
