from flask.ext.api import FlaskAPI
from flask import request, current_app, abort
from functools import wraps

app = FlaskAPI(__name__)
app.config.from_object('settings')


def token_auth(f):
    @wraps(f)
    def decorated_function(*args, **kwargs):
        if request.headers.get('X-API-TOKEN', None) != current_app.config['API_TOKEN']:
            abort(403)
        return f(*args, **kwargs)
    return decorated_function


@app.route('/predict', methods=['POST'])
@token_auth
def predict():
    from engines import content_engine
    item = request.data.get('item')
    num_predictions = request.data.get('num', 10)
    if not item:
        return []
    res =  content_engine.predict(str(item), num_predictions)
    #print res
    return res


@app.route('/train')
@token_auth
def train():
    from engines import content_engine
    print "data:"
    print request.args
    print "end."
    
    data_url = request.data.get('data-url', None)
    print "a"
    content_engine.train(data_url)
    return {"message": "Success!", "success": 1}


if __name__ == '__main__':
    app.debug = True
    app.run(host='0.0.0.0')
