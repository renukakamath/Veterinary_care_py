from flask import Blueprint,render_template,request,redirect,url_for,session,flash
from database import*

doctor=Blueprint("doctor",__name__)

@doctor.route("/doctor_home")
def doctor_home():
	if not session.get("lid") is None:
		return render_template("doctor_home.html")
	else:
		return redirect(url_for("public.login"))

@doctor.route("/doctor_chat_with_patient")
def doctor_chat_with_patient():
	if not session.get("lid") is None:
		data={}
		q="SELECT *,concat(first_name,' ',last_name) as uname FROM `users` inner join pets using(user_id) "
		res=select(q)
		data['msgs']=res
		return render_template("doctor_chat_with_patient.html",data=data)
	else:
		return redirect(url_for("public.login"))

@doctor.route("/doctor_chat_message",methods=['get','post'])
def doctor_chat_message():
	if not session.get("lid") is None:
		data={} 
		cid=request.args['cid']
		qry="select *,concat(first_name,' ',last_name) as name from users where user_id='%s'"%(cid)
		result=select(qry)
		data['name']=result
		if 'submit' in request.form:
			message=request.form['msg']
			q2="INSERT INTO chat(sender_id,sender_type,receiver_id,receiver_type,message,`date_time`)VALUES('%s','doctor',(SELECT `login_id` FROM `users` WHERE `user_id`='%s'),'user','%s',NOW())"%(session['lid'],cid,message)
			insert(q2)
			return redirect(url_for('doctor.doctor_chat_message',cid=cid))
		q="SELECT * FROM chat WHERE (`sender_id`='%s' AND sender_type='doctor' AND `receiver_id`=(SELECT `login_id` FROM `users` WHERE `user_id`='%s')) OR (`sender_id`=(SELECT `login_id` FROM `users` WHERE `user_id`='%s') AND `receiver_id`='%s' AND receiver_type='doctor')"%(session['lid'],cid,cid,session['lid'])
		res=select(q)
		data['msg']=res
		return render_template("doctor_chat_message.html",data=data)
	else:
		return redirect(url_for("public.login"))

@doctor.route("/doctor_view_patient_disease_history")
def doctor_view_patient_disease_history():
	if not session.get("lid") is None:
		data={}
		q="SELECT *,CONCAT(first_name,' ',last_name) AS uname FROM `users`  INNER JOIN pets USING(user_id) INNER JOIN bookings USING(pets_id)INNER JOIN `schedule` USING(schedule_id) WHERE doctor_id='%s' GROUP BY (pets_id)"%(session['did'])
		res=select(q)
		data['msgs']=res
		return render_template("doctor_view_patient_disease_history.html",data=data)
	else:
		return redirect(url_for("public.login"))

# @doctor.route("/doctor_view_patient_physical_condition")
# def doctor_view_patient_physical_condition():
# 	if not session.get("lid") is None:
# 		data={}
# 		q="SELECT *,concat(first_name,' ',last_name) as name FROM `users`"
# 		res=select(q)
# 		data['msgs']=res
# 		return render_template("doctor_view_patient_physical_condition.html",data=data)
# 	else:
# 		return redirect(url_for("public.login"))

@doctor.route("/doctor_view_patients")
def doctor_view_patients():
	if not session.get("lid") is None:
		data={}
		q="SELECT *,CONCAT(first_name,' ',last_name) AS uname FROM users INNER JOIN pets USING(user_id) INNER JOIN bookings USING(pets_id) GROUP BY (pets_id)"
		data['view']=select(q)

		if 'action' in request.args:
			action=request.args['action']
			id=request.args['id']
		else:
			action=None

		if action=='accept':
			q="update bookings set status='accept' where book_id='%s'"%(id)
			update(q)
			flash("Updated")
			return redirect(url_for("doctor.doctor_view_patients"))

		if action=='reject':
			q="update bookings set status='reject' where book_id='%s'"%(id)
			update(q)
			flash("Updated")
			return redirect(url_for("doctor.doctor_home"))

		return render_template("doctor_view_patients.html",data=data)
	else:
		return redirect(url_for("public.login"))

# @doctor.route("/doctor_view_physical_condition",methods=['get','post'])
# def doctor_view_physical_condition():
# 	if not session.get("lid") is None:
# 		data={}
# 		pid=request.args['cid']
# 		q="SELECT *,CONCAT(first_name,' ',last_name) AS name FROM patients INNER JOIN physical_conditions USING(patient_id) where patient_id='%s' ORDER BY date_time DESC LIMIT 1"%(pid)
# 		data['view']=select(q)
# 		return render_template("doctor_view_physical_condition.html",data=data)
# 	else:
# 		return redirect(url_for("public.login"))

@doctor.route("/doctor_view_disease_history")
def doctor_view_disease_history():
	if not session.get("lid") is None:
		data={}
		pid=request.args['cid']
		q="SELECT *,CONCAT(first_name,' ',last_name) AS uname FROM users inner join pets using(user_id) INNER JOIN bookings USING(pets_id)   where user_id='%s'"%(pid)
		data['view']=select(q)
		return render_template("doctor_view_disease_history.html",data=data)
	else:
		return redirect(url_for("public.login"))

@doctor.route("/doctor_add_prescription",methods=['get','post'])
def doctor_add_prescription():
	if not session.get("lid") is None:
		data={}
		book_id=request.args['book_id']
		q="select * from medicines"
		data['med']=select(q)
	
		if 'submit' in request.form:
			med_id=request.form['med_id']
			desc=request.form['desc']
			q="INSERT INTO medicine_prescriptions VALUES(NULL,'%s','%s',NOW(),'%s','Pending')"%(book_id,med_id,desc)
			insert(q)
			return redirect(url_for("doctor.doctor_add_prescription",book_id=book_id))
		q="SELECT *,CONCAT(first_name,' ',last_name) AS uname FROM users inner join pets using(user_id) INNER JOIN bookings USING(pets_id)  WHERE bookings.book_id='%s' "%(book_id)
		data['view']=select(q)
		q="SELECT *,CONCAT(first_name,' ',last_name) AS uname FROM users inner join pets using(user_id) INNER JOIN bookings USING(pets_id) INNER JOIN medicine_prescriptions USING(book_id) INNER JOIN medicines USING(medicine_id) WHERE bookings.book_id='%s' order by medicine_prescriptions.date_time desc"%(book_id)
		data['viewdet']=select(q)
		return render_template("doctor_add_prescription.html",data=data)
	else:
		return redirect(url_for("public.login"))

@doctor.route("/doctor_lab_tests",methods=['get','post'])
def doctor_lab_tests():
	if not session.get("lid") is None:
		data={}
		book_id=request.args['book_id']
		q="SELECT * FROM lab_tests"
		data['lab']=select(q)
		if 'submit' in request.form:
			lab_test_id=request.form['lab_test_id']
			desc=request.form['desc']
			q="INSERT INTO test_prescription(`book_id`,`test_id`,`lab_pres_description`,`report_description`,`date_time`,status) VALUES('%s','%s','%s','NA',NOW(),'Pending')"%(book_id,lab_test_id,desc)
			insert(q)
			return redirect(url_for("doctor.doctor_lab_tests",book_id=book_id))
		q="SELECT *,CONCAT(first_name,' ',last_name) AS uname FROM users inner join pets using(user_id) INNER JOIN bookings USING(pets_id)  WHERE bookings.book_id='%s' "%(book_id)
		data['view']=select(q)
		q="SELECT *,CONCAT(first_name,' ',last_name) AS uname FROM users inner join pets using(user_id) INNER JOIN bookings USING(pets_id) INNER JOIN test_prescription USING(book_id) INNER JOIN lab_tests on lab_test_id=test_prescription.test_id WHERE book_id='%s' order by test_prescription.date_time desc"%(book_id)
		data['viewdet']=select(q)
		return render_template("doctor_lab_tests.html",data=data)
	else:
		return redirect(url_for("public.login"))

@doctor.route("/doctor_add_medical_notes",methods=['get','post'])
def doctor_add_medical_notes():
	if not session.get("lid") is None:
		data={}
		book_id=request.args['book_id']
		if 'submit' in request.form:
			desc=request.form['desc']
			q="INSERT INTO medical_notes(`booking_id`,`description`,`date_time`)VALUES('%s','%s',NOW())"%(book_id,desc)
			insert(q)
			flash("Medical note added")
			return redirect(url_for("doctor.doctor_add_medical_notes",book_id=book_id))
		q1="SELECT *,CONCAT(first_name,' ',last_name) AS uname FROM users inner join pets using(user_id) INNER JOIN bookings USING(pets_id)  where book_id='%s' "%(book_id)
		data['view1']=select(q1)
		q="SELECT *,CONCAT(first_name,' ',last_name) AS uname FROM users inner join pets using(user_id) INNER JOIN bookings USING(pets_id) INNER JOIN medical_notes ON booking_id=bookings.`book_id` where booking_id='%s' order by medical_notes.date_time desc"%(book_id)
		data['view']=select(q)
		return render_template("doctor_add_medical_notes.html",data=data)
	else:
		return redirect(url_for("public.login"))

@doctor.route("/doctor_prev",methods=['get','post'])
def doctor_prev():
	if not session.get("lid") is None:
		data={}
		book_id=request.args['book_id']
		q="SELECT *,CONCAT(first_name,' ',last_name) AS uname FROM users inner join pets using(user_id) INNER JOIN bookings USING(pets_id) INNER JOIN test_prescription USING(book_id) WHERE bookings.book_id='%s' order by test_prescription.date_time desc"%(book_id)
		data['view']=select(q)
		q="SELECT *,CONCAT(first_name,' ',last_name) AS uname FROM users inner join pets using(user_id) INNER JOIN bookings USING(pets_id) INNER JOIN test_prescription USING(book_id) INNER JOIN lab_tests on lab_test_id=test_prescription.test_id WHERE book_id='%s' and report_description!='NA' order by test_prescription.date_time desc"%(book_id)
		data['viewdet']=select(q)
		return render_template("doctor_prev.html",data=data)
	else:
		return redirect(url_for("public.login"))



@doctor.route("/doctor_schedule_doctor_consulting_times",methods=['get','post'])
def doctor_schedule_doctor_consulting_times():
	if not session.get("lid") is None:
		data={}
		if 'ids' in request.args:
			ids=request.args['ids']
			q="delete from SCHEDULE where schedule_id='%s'"%(ids)
			delete(q)
			flash("Removed!")
			return redirect(url_for("doctor.doctor_schedule_doctor_consulting_times"))
		if 'submit' in request.form:
			# did=request.form['did']
			stime=request.form['stime']
			etime=request.form['etime']
			date=request.form['date']
			fee=request.form['fee']
			q="INSERT INTO SCHEDULE (doctor_id,`start_time`,`end_time`,`date`,`fee_amount`) VALUES('%s','%s','%s','%s','%s')"%(session['did'],stime,etime,date,fee)
			insert(q)
			flash("Schedule committed")
			return redirect(url_for("doctor.doctor_schedule_doctor_consulting_times"))
		q="select *,concat(first_name,' ',last_name) as doctor_name from doctors"
		data['did']=select(q)
		q="select *,concat(first_name,' ',last_name) as doctor_name from doctors inner join schedule using(doctor_id)"
		data['view']=select(q)
		return render_template("doctor_schedule_doctor_consulting_times.html",data=data)
	else:
		return redirect(url_for("public.login"))

@doctor.route("/add_vaccination",methods=['get','post'])
def add_vaccination():
	data={}
	q="select * from vaccination"
	res=select(q)
	data['ressss']=res
	
	if "md_amount" in request.form:

		vaccination=request.form['vaccination']
		amount=request.form['amount']
		date=request.form['date']
		
		q="insert into vaccination values(null,'%s','%s','%s')"%(vaccination,date,amount)
		insert(q)
		flash("Schedule committed")
		return redirect(url_for("doctor.add_vaccination"))
		
		return render_template("add_vaccination.html",data=data)
	else:
		return redirect(url_for("public.login"))