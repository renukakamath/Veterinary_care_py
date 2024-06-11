from flask import *
from database import*
import uuid

shop=Blueprint("shop",__name__)

@shop.route("/shop_home")
def shop_home():
	if not session.get("lid") is None:
		return render_template("shop_home.html")
	else:
		return redirect(url_for("public.login"))

@shop.route("/shop_add_product",methods=['get','post'])
def shop_add_product():
	if not session.get("lid") is None:
		data={}
		if 'action' in request.args:
			action=request.args['action']
			id=request.args['id']
		else:
			action=None
		if action=='update':
			q="select * from product where product_id='%s'"%(id)
			data['view_update']=select(q)

		if 'update_submit' in request.form:
			pdt=request.form['pdt']
			amt=request.form['amt']
			
			q="update product set product='%s',amount='%s' where product_id='%s'"%(pdt,amt,id)
			update(q)
			flash("Changes Saved")
			return redirect(url_for("shop.shop_add_product"))

		if action=='delete':
			q="delete from product where product_id='%s'"%(id)
			delete(q)
			flash("Deleted")
			return redirect(url_for("shop.shop_add_product"))
		if 'submit' in request.form:
			pdt=request.form['pdt']
			amt=request.form['amt']
			qty=request.form['qty']
			i=request.files['img']
			path='static/uploads/'+str(uuid.uuid4())+i.filename
			i.save(path)
			
			q="INSERT INTO product VALUES(NULL,'%s','%s','%s','%s',curdate(),'%s')"%(session['sid'],pdt,amt,qty,path)
			insert(q)
			flash("Added")
			return redirect(url_for("shop.shop_add_product"))
		q="SELECT * FROM product "
		data['view']=select(q)
		return render_template("shop_add_product.html",data=data)
	else:
		return redirect(url_for("public.login"))


@shop.route("/shop_view_order",methods=['get','post'])
def shop_view_order():
	if not session.get("lid") is None:
		data={}
		
		q="SELECT * FROM `order` INNER JOIN users USING (user_id) INNER JOIN product USING (product_id)"
		data['view']=select(q)
		return render_template("shop_view_order.html",data=data)
	else:
		return redirect(url_for("public.login"))