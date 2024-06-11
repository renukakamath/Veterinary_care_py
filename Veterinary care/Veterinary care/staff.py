from flask import *
from database import*

staff=Blueprint("staff",__name__)

@staff.route("/staff_home")
def staff_home():
	if not session.get("lid") is None:
		return render_template("staff_home.html")
	else:
		return redirect(url_for("public.login"))

@staff.route("/staff_assign",methods=['get','post'])
def staff_assign():
	if not session.get("lid") is None:
		data={} 
		q="SELECT * FROM staff INNER JOIN assign_pet USING(staff_id) INNER  JOIN pets USING(pets_id) where staff_id='%s'"%(session['sid'])
		data['staff']=select(q)


		if 'action' in request.args:
			action=request.args['action']
			id=request.args['id']
		else:
			action=None

		if action=='accept':
			q="update assign_pet set status='accept' where assign_id='%s'"%(id)
			delete(q)
			flash("accept..!")
			return redirect(url_for("staff.staff_assign"))

		if action=='reject':
			q="update assign_pet set status='reject' where assign_id='%s'"%(id)
			delete(q)
			flash("reject..!")
			return redirect(url_for("staff.staff_assign"))
	
		return render_template("staff_assign_pet.html",data=data)
	else:
		return redirect(url_for("public.login"))




	
