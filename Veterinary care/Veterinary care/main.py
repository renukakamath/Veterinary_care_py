from flask import Flask
from public import public
from admin import admin
from laboratory import laboratory
from doctor import doctor
from pharmacy import pharmacy
from shop import shop
from staff import staff


from api import api

app=Flask(__name__)

app.secret_key="secret_key"

app.register_blueprint(public)
app.register_blueprint(admin,url_prefix="/admin")
app.register_blueprint(laboratory,url_prefix="/laboratory")
app.register_blueprint(doctor,url_prefix="/doctor")
app.register_blueprint(pharmacy,url_prefix="/pharmacy")
app.register_blueprint(shop,url_prefix="/shop")
app.register_blueprint(staff,url_prefix="/staff")

app.register_blueprint(api,url_prefix="/api")

app.run(debug=True,port=5018,host="0.0.0.0")