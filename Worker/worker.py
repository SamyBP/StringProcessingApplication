import json


s = json.loads('{"id":"f90c75dc-b581-4385-9184-1bf18e2bf805","input":"input","callback":"http://localhost:8001/callback","tasks":[{"type":"SUBSTRING","args":{"start":1,"end":2}}]}')

print(s["id"])
