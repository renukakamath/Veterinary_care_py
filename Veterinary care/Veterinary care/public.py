from flask import Blueprint,render_template,request,redirect,url_for,session,flash
from database import*

public=Blueprint("public",__name__)

@public.route("/")
def home():
	session.clear()
	return render_template("home.html")

@public.route('/login',methods=['get','post'])
def login():
	session.clear()
	if 'submit' in request.form:
		uname=request.form['uname']
		passs=request.form['passs']
		q="select * from login where username='%s' and password='%s'" %(uname,passs)
		res=select(q)
		if res:
			session['lid']=res[0]['login_id']
			if res[0]['usertype']=="admin":
				flash("Logging in")			
				return redirect(url_for("admin.admin_home"))

			elif res[0]['usertype']=="pharmacy":
				q="select * from pharmacy where login_id='%s'"%(res[0]['login_id'])
				res1=select(q)
				if res1:
					session['pid']=res1[0]['pharmacy_id']
					flash("Logging in")
					return redirect(url_for("pharmacy.pharmacy_home"))

			elif res[0]['usertype']=="doctor":
				q="select * from doctors where login_id='%s'"%(res[0]['login_id'])
				res1=select(q)
				if res1:
					session['did']=res1[0]['doctor_id']
					flash("Logging in")
					return redirect(url_for("doctor.doctor_home"))

			elif res[0]['usertype']=="lab":
				q="select * from laboratory where login_id='%s'"%(res[0]['login_id'])
				res1=select(q)
				if res1:
					session['lbid']=res1[0]['lab_id']
					flash("Logging in")
					return redirect(url_for("laboratory.laboratory_home"))

			elif res[0]['usertype']=="shop":
				q="select * from shop where login_id='%s'"%(res[0]['login_id'])
				res1=select(q)
				if res1:
					session['sid']=res1[0]['shop_id']
					flash("Logging in")
					return redirect(url_for("shop.shop_home"))

			elif res[0]['usertype']=="patient":
				q="select * from patients where login_id='%s'"%(res[0]['login_id'])
				res1=select(q)
				if res1:
					session['pid']=res1[0]['patient_id']
					flash("Logging in")
					# android portion
					return redirect(url_for("admin.admin_home"))

			elif res[0]['usertype']=="staff":
				q="select * from staff where login_id='%s'"%(res[0]['login_id'])
				res1=select(q)
				if res1:
					session['sid']=res1[0]['staff_id']
					flash("Logging in")
					# android portion
					return redirect(url_for("staff.staff_home"))
			else:
				flash("Registration Under Process")
		flash("You are Not Registered")
	return render_template("login.html")


@public.route("/staffreg",methods=['get','post'])
def staffreg():
	
		if 'submit' in request.form:
			fname=request.form['fname']
			lname=request.form['lname']
			phone=request.form['phone']
			email=request.form['email']
			ty=request.form['typ']
			uname=request.form['uname']
			pas=request.form['passs']
			lat=request.form['lat']
			lon=request.form['lon']
			q="INSERT INTO login VALUES(NULL,'%s','%s','staff')"%(uname,pas)
			ids=insert(q)
			q="INSERT INTO staff  VALUES(null,'%s','%s','%s','%s','%s','%s','%s','%s')"%(ids,fname,lname,phone,email,ty,lat,lon)
			insert(q)
			flash("Staff Registered")
			return redirect(url_for("public.login"))
			
		return render_template("staff_reg.html")
	


