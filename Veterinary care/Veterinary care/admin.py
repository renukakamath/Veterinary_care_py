from flask import Blueprint,render_template,request,redirect,url_for,session,flash
from database import*
import uuid

admin=Blueprint("admin",__name__)

@admin.route("/admin_home")
def admin_home():
	if not session.get("lid") is None:
		return render_template("admin_home.html")
	else:
		return redirect(url_for("public.login"))

@admin.route("/admin_doctor_registration",methods=['get','post'])
def admin_doctor_registration():
	if not session.get("lid") is None:
		data={}
		q="select *,concat(first_name,' ',last_name) as fullname from doctors inner join login using(login_id)"
		data['view']=select(q)
		if 'action' in request.args:
			action=request.args['action']
			id=request.args['id']
		else:
			action=None
		if action=='update':
			q="select * from doctors where login_id='%s'"%(id)
			data['view_update']=select(q)

		if 'update_submit' in request.form:
			fname=request.form['fname']
			lname=request.form['lname']
			phone=request.form['phone']
			email=request.form['email']
			place=request.form['place']
			


			qlf=request.form['qlf']
			q="update login  set username='%s',password='%s' where login_id='%s'"%(email,phone,id)
			update(q)
			q="update doctors set `first_name`='%s',`last_name`='%s',`phone`='%s',`email`='%s',`place`='%s',`qualification`='%s' where login_id='%s'"%(fname,lname,phone,email,place,qlf,id)
			update(q)
			flash("Changed")
			return redirect(url_for("admin.admin_doctor_registration"))

		if action=='delete':
			q="delete from login where login_id='%s'"%(id)
			delete(q)
			q="delete from doctors where login_id='%s'"%(id)
			delete(q)
			flash("Deleted")
			return redirect(url_for("admin.admin_doctor_registration"))

		if 'submit' in request.form:
			fname=request.form['fname']
			lname=request.form['lname']
			phone=request.form['phone']
			email=request.form['email']
			place=request.form['place']
			qlf=request.form['qlf']
			i=request.files['img']
			path="static/image"+str(uuid.uuid4())+i.filename
			i.save(path)

			iss=request.files['imgss']
			paths="static/image"+str(uuid.uuid4())+iss.filename
			iss.save(paths)
			q="INSERT INTO login VALUES(NULL,'%s','%s','doctor')"%(email,phone)
			ids=insert(q)
			q="INSERT INTO doctors(`login_id`,`first_name`,`last_name`,Image,`phone`,`email`,`place`,`qualification`,`achievements`) VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s')"%(ids,fname,lname,path,phone,email,place,qlf,paths)
			insert(q)
			flash("Doctor Registered")
			return redirect(url_for("admin.admin_doctor_registration"))
		return render_template("admin_doctor_registration.html",data=data)
	else:
		return redirect(url_for("public.login"))

@admin.route("/admin_laboratory_registration",methods=['get','post'])
def admin_laboratory_registration():
	if not session.get("lid") is None:
		data={}
		q="select * from laboratory inner join login using(login_id)"
		data['view']=select(q)
		if 'action' in request.args:
			action=request.args['action']
			id=request.args['id']
		else:
			action=None
		if action=='update':
			q="select * from laboratory where login_id='%s'"%(id)
			data['view_update']=select(q)
		if 'update_submit' in request.form:
			lname=request.form['pname']
			phone=request.form['phone']
			email=request.form['email']
			q="update login set username='%s',password='%s' where login_id='%s'"%(email,phone,id)
			update(q)
			q="update laboratory set lab_name='%s',phone='%s',email='%s' where login_id='%s'"%(lname,phone,email,id)
			update(q)
			flash("Changes Saved")
			return redirect(url_for("admin.admin_laboratory_registration"))

		if action=='delete':
			q="delete from login where login_id='%s'"%(id)
			delete(q)
			q="delete from laboratory where login_id='%s'"%(id)
			delete(q)
			flash("Deleted")
			return redirect(url_for("admin.admin_laboratory_registration"))

		if 'submit' in request.form:
			lname=request.form['pname']
			phone=request.form['phone']
			email=request.form['email']
			q="INSERT INTO login VALUES(NULL,'%s','%s','lab')"%(email,phone)
			iid=insert(q)
			q="INSERT INTO laboratory VALUES(NULL,'%s','%s','%s','%s')"%(iid,lname,phone,email)
			insert(q)
			flash("Laboratory Registered")
			return redirect(url_for("admin.admin_laboratory_registration"))
		return render_template("admin_laboratory_registration.html",data=data)
	else:
		return redirect(url_for("public.login"))

@admin.route("/admin_shop_registration",methods=['get','post'])
def admin_shop_registration():
	if not session.get("lid") is None:
		data={}
		q="select * from shop inner join login using(login_id)"
		data['view']=select(q)
		if 'action' in request.args:
			action=request.args['action']
			id=request.args['id']
		else:
			action=None
		if action=='update':
			q="select * from shop where login_id='%s'"%(id)
			data['view_update']=select(q)
		if 'update_submit' in request.form:
			lname=request.form['pname']
			phone=request.form['phone']
			email=request.form['email']
			q="update login set username='%s',password='%s' where login_id='%s'"%(email,phone,id)
			update(q)
			q="update shop set name='%s',phone='%s',email='%s' where login_id='%s'"%(lname,phone,email,id)
			update(q)
			flash("Changes Saved")
			return redirect(url_for("admin.admin_shop_registration"))

		if action=='delete':
			q="delete from login where login_id='%s'"%(id)
			delete(q)
			q="delete from shop where login_id='%s'"%(id)
			delete(q)
			flash("Deleted")
			return redirect(url_for("admin.admin_shop_registration"))

		if 'submit' in request.form:
			lname=request.form['pname']
			phone=request.form['phone']
			email=request.form['email']
			lat=request.form['lat']
			lon=request.form['lon']
			q="INSERT INTO login VALUES(NULL,'%s','%s','shop')"%(email,phone)
			iid=insert(q)
			q="INSERT INTO shop VALUES(NULL,'%s','%s','%s','%s','%s','%s')"%(iid,lname,phone,email,lat,lon)
			insert(q)
			flash("shop Registered")
			return redirect(url_for("admin.admin_shop_registration"))
		return render_template("admin_shop_registration.html",data=data)
	else:
		return redirect(url_for("public.login"))

@admin.route("/admin_pharmacy_registration",methods=['get','post'])
def admin_pharmacy_registration():
	if not session.get("lid") is None:
		data={}
		q="select * from pharmacy inner join login using(login_id)"
		data['view']=select(q)
		if 'action' in request.args:
			action=request.args['action']
			id=request.args['id']
		else:
			action=None
		if action=='update':
			q="select * from pharmacy where login_id='%s'"%(id)
			data['view_update']=select(q)
		if 'update_submit' in request.form:
			lname=request.form['pname']
			phone=request.form['phone']
			email=request.form['email']
			q="update login set username='%s',password='%s' where login_id='%s'"%(email,phone,id)
			update(q)
			q="update pharmacy set pharmacy_name='%s',phone='%s',email='%s' where login_id='%s'"%(lname,phone,email,id)
			update(q)
			flash("Changes Saved")
			return redirect(url_for("admin.admin_pharmacy_registration"))

		if action=='delete':
			q="delete from login where login_id='%s'"%(id)
			delete(q)
			q="delete from pharmacy where login_id='%s'"%(id)
			delete(q)
			flash("Deleted")
			return redirect(url_for("admin.admin_pharmacy_registration"))

		if 'submit' in request.form:
			pname=request.form['pname']
			email=request.form['email']
			phone=request.form['phone']
			q="INSERT INTO login VALUES(NULL,'%s','%s','pharmacy')"%(email,phone)
			id=insert(q)
			q="INSERT INTO pharmacy VALUES(NULL,'%s','%s','%s','%s')"%(id,pname,email,phone)
			insert(q)
			flash("Pharmacy Registered")
			return redirect(url_for("admin.admin_pharmacy_registration"))
		return render_template("admin_pharmacy_registration.html",data=data)
	else:
		return redirect(url_for("public.login"))

@admin.route("/admin_schedule_times",methods=['get','post'])
def admin_schedule_times():
	if not session.get("lid") is None:
		data={}
		
		q="select *,concat(first_name,' ',last_name) as doctor_name from doctors"
		data['did']=select(q)
		q="select *,concat(first_name,' ',last_name) as doctor_name from doctors inner join schedule using(doctor_id)"
		data['view']=select(q)
		return render_template("admin_schedule_times.html",data=data)
	else:
		return redirect(url_for("public.login"))

@admin.route("/admin_view_patients")
def admin_view_patients():
	if not session.get("lid") is None:
		data={}
		q="SELECT *,concat(first_name,' ',last_name) as name FROM users"
		data['view']=select(q)
		return render_template("admin_view_patients.html",data=data)
	else:
		return redirect(url_for("public.login"))


@admin.route("/admin_view_users_pet")
def admin_view_users_pet():
	if not session.get("lid") is None:
		data={}
		user_id=request.args['user_id']
		q="SELECT * FROM `pets` WHERE `user_id`='%s'"%(user_id)
		data['view']=select(q)
		return render_template("admin_view_users_pet.html",data=data)
	else:
		return redirect(url_for("public.login"))

@admin.route("/admin_view_payment_details")
def admin_view_payment_details():
	if not session.get("lid") is None:
		data={}
		book_id=request.args['book_id']
		q="SELECT *,CONCAT(first_name,' ',last_name) AS NAME FROM payments INNER JOIN bookings USING(book_id) INNER JOIN `pets` USING(pets_id) INNER JOIN users USING(user_id) where book_id='%s'"%(book_id)
		data['view']=select(q)
		return render_template("admin_view_payment_details.html",data=data)
	else:
		return redirect(url_for("public.login"))



@admin.route("/admin_view_request")
def admin_view_request():
	if not session.get("lid") is None:
		data={}
		
		q="SELECT * FROM `request` inner join pets using(pets_id) "
		data['view']=select(q)
		return render_template("admin_view_request.html",data=data)
	else:
		return redirect(url_for("public.login"))

@admin.route("/admin_assign",methods=['get','post'])
def admin_assign():
	if not session.get("lid") is None:
		data={}
		q="select * from staff"
		data['view']=select(q)

		q="select * from staff inner join assign_pet using(staff_id)"
		data['staff']=select(q)

		
		if 'submit' in request.form:
			staff=request.form['staff']
			id=request.args['id']
			rid=request.args['rid']
			q="insert into assign_pet values(null,'%s','%s','%s',curdate(),'pending')"%(id,rid,staff)
			insert(q)
			q="update request set status='assigned' where request_id='%s'"%(rid)
			update(q)
			return redirect(url_for('admin.admin_view_request')) 
			
		return render_template("admin_assign_pet.html",data=data)
	else:
		return redirect(url_for("public.login"))


@admin.route('/admin_viewbookings')
def admin_viewbookings():
	data={}
	q="SELECT * FROM `bookings` INNER JOIN `schedule` USING (`schedule_id`) INNER JOIN `pets` USING (`pets_id`) INNER JOIN `doctors` USING (`doctor_id`)"
	res=select(q)
	data['admin_viewbookings']=res

	return render_template('admin_viewbookings.html',data=data)




