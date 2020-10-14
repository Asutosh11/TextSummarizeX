from gevent import monkey
monkey.patch_all()

from gevent.pywsgi import WSGIServer
from api import app

http_server = WSGIServer(('0.0.0.0', 8000), app)
http_server.serve_forever()