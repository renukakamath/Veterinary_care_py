from flask import Blueprint,render_template,request,redirect,url_for,session,flash
from database import*

laboratory=Blueprint("laboratory",__name__)

@laboratory.route("/laboratory_home")
def laboratory_home():
	if not session.get("lid") is None:
		return render_template("laboratory_home.html")
	else:
		return redirect(url_for("public.login"))

@laboratory.route("/laboratory_manage_test",methods=['get','post'])
def laboratory_manage_test():
	if not session.get("lid") is None:
		data={}
		if 'action' in request.args:
			action=request.args['action']
			id=request.args['id']
		else:
			action=None
		if action=='update':
			q="select * from lab_tests where lab_test_id='%s'"%(id)
			data['view_update']=select(q)

		if 'update_submit' in request.form:
			tname=request.form['tname']
			desc=request.form['desc']
			rate=request.form['rate']
			q="update lab_tests set test_name='%s',description='%s',rate='%s' where lab_test_id='%s'"%(tname,desc,rate,id)
			update(q)
			flash("Changes Saved")
			return redirect(url_for("laboratory.laboratory_manage_test"))

		if action=='delete':
			q="delete from lab_tests where lab_test_id='%s'"%(id)
			delete(q)
			flash("Deleted")
			return redirect(url_for("laboratory.laboratory_manage_test"))
		if 'submit' in request.form:
			tname=request.form['tname']
			desc=request.form['desc']
			rate=request.form['rate']
			q="INSERT INTO lab_tests VALUES(NULL,'%s','%s','%s','%s')"%(session['lbid'],tname,desc,rate)
			insert(q)
			flash("Added")
			return redirect(url_for("laboratory.laboratory_manage_test"))
		q="SELECT * FROM lab_tests WHERE lab_id='%s'"%(session['lbid'])
		data['view']=select(q)
		return render_template("laboratory_manage_test.html",data=data)
	else:
		return redirect(url_for("public.login"))

@laboratory.route("/laboratory_post_lab_test_reports",methods=['get','post'])
def laboratory_post_lab_test_reports():
	if not session.get("lid") is None:
		data={}
		q="SELECT *,CONCAT(first_name,' ',last_name) AS user_name FROM test_prescription INNER JOIN lab_tests ON lab_test_id=test_prescription.`test_id` INNER JOIN bookings USING(book_id) inner join users on users.user_id ORDER BY `test_pres_id` DESC"
		data['view']=select(q)
		i=1
		for row in data['view']:
			if 'submit'+str(i) in request.form:
				rd=request.form['rd'+str(i)]
				id=request.form['test_pres_id'+str(i)]
				test_rate=request.form['test_rate'+str(i)]
				book_id=request.form['book_id'+str(i)]
				q="INSERT INTO `payments` VALUES(NULL,'%s','%s','NA','Lab')"%(book_id,test_rate)
				insert(q)
				q="update test_prescription set report_description='%s',`status`='Accept' where test_pres_id='%s'"%(rd,id)
				update(q)

				return redirect(url_for("laboratory.laboratory_post_lab_test_reports"))
			i=i+1
		return render_template("laboratory_post_lab_test_reports.html",data=data)
	else:
		return redirect(url_for("public.login"))

@laboratory.route("/laboratory_view_patient_details")
def laboratory_view_patient_details():
	if not session.get("lid") is None:
		data={}
		q="SELECT *,concat(first_name,' ',last_name) as uname FROM users inner join `pets` using(`user_id`)"
		data['view']=select(q)
		return render_template("laboratory_view_patient_details.html",data=data)
	else:
		return redirect(url_for("public.login"))

@laboratory.route("/laboratory_view_prescribed_test",methods=['get','post'])
def laboratory_view_prescribed_test():
	if not session.get("lid") is None:
		data={}
		pid=request.args['pid']
		q="SELECT *,CONCAT(first_name,' ',last_name) AS patient_name FROM test_prescription INNER JOIN lab_tests ON lab_test_id=test_prescription.`test_id` INNER JOIN bookings USING(book_id) INNER JOIN pets USING(pets_id) INNER JOIN users USING(user_id) WHERE user_id='%s' AND `test_prescription`.`status`='Forward'"%(pid)
		data['view']=select(q)
		return render_template("laboratory_view_prescribed_test.html",data=data)
	else:
		return redirect(url_for("public.login"))