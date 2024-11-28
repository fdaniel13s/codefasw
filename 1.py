import requests
from flask import Flask, request, jsonify

app = Flask(__name__)

CATALOGO_URL = "http://34.207.108.141:8080/Cloud/catalogo/2"

@app.route('/add_mantenimiento', methods=['POST'])
def add_mantenimiento():
    data = request.json
    mantenimiento = {
        "fecha": "07/02/2024",
        "hora": "18:43:17",
        "usuario": "jdelgad@gmail.com",
        "observacion": "Se realizará la actualización."
    }
    
    # Agregar el campo 'mantenimiento' al JSON recibido
    data['mantenimiento'] = [mantenimiento]
    
    # Enviar el nuevo JSON al servicio del catálogo
    response = requests.post(CATALOGO_URL, json=data)
    
    if response.status_code == 200:
        return jsonify({"status": "success", "response": response.json()}), 200
    else:
        return jsonify({"status": "error", "message": "Failed to update catalog"}), 400

if __name__ == '__main__':
    app.run(port=5000)
