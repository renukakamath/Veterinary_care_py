from flask import *
from database import *

import demjson
import uuid


api=Blueprint('api',__name__)

@api.route('/login',methods=['get','post'])
def login(): 
	data={}
	
	username = request.args['username']
	password = request.args['password']
	q="SELECT * from login where username='%s' and password='%s'" % (username,password)
	res = select(q)
	if res :
		data['status']  = 'success'
		data['data'] = res
		data['method']='login'
	else:
		data['status']	= 'failed'
		data['method']='login'
	return  demjson.encode(data)

@api.route('/userregister',methods=['get','post'])
def userregister():

	data = {}

	fname=request.args['fname'] 
	lname=request.args['lname']
	phone=request.args['phone']
	email=request.args['email']
	hname=request.args['hname']
	place=request.args['place']
	latitude=request.args['latitude']
	longitude=request.args['longitude']
	username=request.args['username']
	password=request.args['password']

	q1="SELECT * FROM login WHERE username='%s'" %(username)
	# print(q1)
	r=select(q1)
	# print(r)
	if r:
		data['status']='duplicate'
		data['method']='userregister'
	else:
		q= "INSERT INTO `login` VALUES(NULL,'%s','%s','user')"%(username,password)
		lid = insert(q)
		qr="INSERT INTO `users` VALUES(NULL,'%s','%s','%s','%s','%s','%s','%s','%s','%s')"%(lid,fname,lname,phone,email,hname,place,latitude,longitude)		
		# print(qr)
		id=insert(qr)

		if id>0:
			data['status'] = 'success'
		else:
			data['status'] = 'failed'
		data['method']='userregister'
	return demjson.encode(data)



@api.route('/User_manage_pets',methods=['get','post'])
def User_manage_pets():

	data={}
	image=request.files['image']
	pet_name=request.form['pet_name']
	pet_type=request.form['type']
	breed=request.form['breed']
	age=request.form['age']
	others=request.form['others']
	login_id=request.form['login_id']
	
	path="static/uploads/"+str(uuid.uuid4())+ ".jpg"
	image.save(path)

	q="INSERT INTO `pets` VALUES(NULL,(SELECT `user_id` FROM `users` WHERE `login_id`='%s'),'%s','%s','%s','%s','%s','%s')"%(login_id,pet_name,pet_type,breed,age,others,path)
	id=insert(q)
	if id>0:
		data['status'] = 'success'
		
	else:
		data['status'] = 'failed'
	data['method'] = 'User_manage_pets'
	return demjson.encode(data)



@api.route('/User_view_pets',methods=['get','post'])
def User_view_pets():
	data={}
	loginid=request.args['loginid']
	
	q="SELECT * FROM `pets` WHERE `user_id`=(SELECT `user_id` FROM `users` WHERE `login_id`='%s')"%(loginid)
	print(q)
	res = select(q)
	print(res)
	if res :
		data['status']  = 'success'
		data['data'] = res
		
	else:
		data['status']	= 'failed'
	data['method']='User_view_pets'
	return  demjson.encode(data)



@api.route('/user_view_doctors',methods=['get','post'])
def user_view_doctors():
	data={}
	
	q="SELECT *,concat(first_name,' ',last_name) as dname FROM `doctors`"
	print(q)
	res = select(q)
	print(res)
	if res :
		data['status']  = 'success'
		data['data'] = res
		
	else:
		data['status']	= 'failed'
	data['method']='user_view_doctors'
	return  demjson.encode(data)



@api.route('/user_view_schedule',methods=['get','post'])
def user_view_schedule():
	data={}
	
	doctor_ids=request.args['doctor_ids']
	
	q="SELECT * FROM `schedule` WHERE `doctor_id`='%s'"%(doctor_ids)

	res = select(q)

	if res :
		data['status']  = 'success'
		data['data'] = res
		
	else:
		data['status']	= 'failed'
	data['method']='user_view_schedule'
	return  demjson.encode(data)


@api.route('/User_pet_book_doctor',methods=['get','post'])
def User_pet_book_doctor():
	data={}
	
	login_id=request.args['login_id']
	
	q="SELECT * FROM `pets`"
	# q="SELECT * FROM `pets` WHERE `user_id`=(SELECT `user_id` FROM `users` WHERE `login_id`='%s')"%(login_id)
	
	res = select(q)

	if res :
		data['status']  = 'success'
		data['data'] = res
		
	else:
		data['status']	= 'failed'
	data['method']='User_pet_book_doctor'
	return  demjson.encode(data)


@api.route('/user_book_doctor',methods=['get','post'])
def user_book_doctor():

	data={}
	pet_id=request.args['pet_id']
	schedule_ids=request.args['schedule_ids']
	fee_amount=request.args['fee_amount']
	lid=request.args['lid']

	q="update pets set user_id=(select user_id from users where login_id='%s') where pets_id='%s' "%(lid,pet_id)
	update(q)
	q= "INSERT INTO `bookings` VALUES(NULL,'%s','%s',NOW(),'Paid')"%(schedule_ids,pet_id)	
	bid=insert(q)
	
	q="INSERT INTO `payments` VALUES(NULL,'%s','%s',CURDATE(),'Doctor',curtime())"%(bid,fee_amount)
	id=insert(q)
	if id>0:
		data['status'] = 'success'
		
	else:
		data['status'] = 'failed'
	data['method'] = 'user_book_doctor'
	return demjson.encode(data)


@api.route('/user_view_bookings',methods=['get','post'])
def user_view_bookings():
	data={}
	
	login_id=request.args['login_id']
	
	# q="SELECT *,CONCAT(`doctors`.`first_name`,' ',`doctors`.`last_name`) AS dname,`schedule`.`date` AS sh_date,`bookings`.`date_time` AS booked_date FROM `bookings` INNER JOIN `schedule` USING(`schedule_id`) INNER JOIN `doctors` USING(`doctor_id`) INNER JOIN `payments` USING(`book_id`) INNER JOIN `pets` USING(`pet_id`)WHERE `user_id`=(SELECT `user_id` FROM `users` WHERE `login_id`='%s')  AND `payment_type`='Medicne' "%(login_id)
	q="SELECT *,CONCAT(`doctors`.`first_name`,' ',`doctors`.`last_name`) AS dname,`schedule`.`date` AS sh_date,`bookings`.`date_time` AS booked_date FROM `doctors`,`schedule`,`bookings`,`pets` WHERE `doctors`.`doctor_id`=`schedule`.`doctor_id` AND `schedule`.`schedule_id`=`bookings`.`schedule_id` AND `bookings`.`pets_id`=`pets`.`pets_id` and user_id=(select user_id from users where login_id='%s') group by pets.pets_id "%(login_id)
	# print(q)
	res = select(q)
	# print(res)
	if res :
		data['status']  = 'success'
		data['data'] = res
		
	else:
		data['status']	= 'failed'
	data['method']='user_view_bookings'
	return  demjson.encode(data)


@api.route('/user_view_medical_history',methods=['get','post'])
def user_view_medical_history():
	data={}
	
	login_id=request.args['login_id']
	
	q="SELECT *,CONCAT(`doctors`.`first_name`,' ',`doctors`.`last_name`) AS dname  FROM `medical_notes`  INNER JOIN `bookings` ON `bookings`.`book_id`=`medical_notes`.`booking_id` INNER JOIN `schedule` USING(`schedule_id`) INNER JOIN `doctors` USING(`doctor_id`) INNER JOIN `pets` USING (pets_id) WHERE pets.`user_id`=(SELECT `user_id` FROM `users` WHERE `login_id`='%s') "%(login_id)
	print(q)
	res = select(q)
	print(res)
	if res :
		data['status']  = 'success'
		data['data'] = res
		 
	else:
		data['status']	= 'failed'
	data['method']='user_view_medical_history'
	return  demjson.encode(data)



@api.route('/user_view_med_pres',methods=['get','post'])
def user_view_med_pres():
	data={}
	
	booking_id=request.args['booking_id']
	
	q="SELECT *,`pharmacy`.`phone` AS ph_phone,`medicine_prescriptions`.`date_time` AS pre_date_time,`medicine_prescriptions`.`status` AS pre_status FROM `medicine_prescriptions` INNER JOIN `medicines` USING(`medicine_id`) INNER JOIN `pharmacy` USING(`pharmacy_id`) WHERE `medicine_prescriptions`.`book_id`='%s'"%(booking_id)
	res = select(q)
	if res :
		data['status']  = 'success'
		data['data'] = res
		
	else:
		data['status']	= 'failed'
	data['method']='user_view_med_pres'  
	return  demjson.encode(data)


@api.route('/user_forward_med_pres',methods=['get','post'])
def user_forward_med_pres():

	data={}
	med_pres_ids=request.args['med_pres_ids']

	q="UPDATE `medicine_prescriptions` SET `status`='Forward' WHERE `med_pres_id`='%s'"%(med_pres_ids)
	print(q)
	id=update(q)
	if id>0:
		data['status'] = 'success'
		
	else:
		data['status'] = 'failed'
	data['method'] = 'user_forward_med_pres'
	return demjson.encode(data)




@api.route('/user_payment',methods=['get','post'])
def user_payment():

	data={}
	action=request.args['action']
	if action=="medicine":
		pay_id=request.args['pay_id']
		med_pres_ids=request.args['med_pres_ids']

		q="UPDATE `payments` SET `payment_date`=CURDATE() WHERE pay_id='%s'"%(pay_id)
		update(q)
		q="UPDATE `medicine_prescriptions` SET `status`='Paid' WHERE `med_pres_id`='%s'"%(med_pres_ids)
		update(q)

		data['status'] = 'success'
	else:
		schedule_id=request.args['schedule_id']
		login_id=request.args['login_id']
		pamount=request.args['pamount']

		q="insert into `bookings` values(null,'%s',(select `patient_id` from `patients` where `login_id`='%s'),curdate(),'book')"%(schedule_id,login_id)
		book_id=insert(q)
		print(q)
		q="insert into `payments` values(null,'%s','%s',curdate(),'doctor')"%(boo_id,pamount)
		insert(q)
		data['status'] = 'success'
	
	data['method'] = 'user_payment'
	return demjson.encode(data)


@api.route('/user_view_test_pres',methods=['get','post'])
def user_view_test_pres():
	data={}
	
	booking_id=request.args['booking_id']
	
	q="SELECT *,`laboratory`.`phone` AS lphone,`test_prescription`.`date_time` AS test_date,`test_prescription`.`status` AS test_status FROM `test_prescription` INNER JOIN `lab_tests` ON `lab_test_id`=`test_id` INNER JOIN `laboratory` USING(`lab_id`) WHERE `test_prescription`.`book_id`='%s'"%(booking_id)
	print(q)
	res = select(q)
	print(res)
	if res :
		data['status']  = 'success'
		data['data'] = res
		
	else:
		data['status']	= 'failed'
	data['method']='user_view_test_pres'
	return  demjson.encode(data)




@api.route('/user_forward_test_pres',methods=['get','post'])
def user_forward_test_pres():

	data={}
	test_pres_ids=request.args['test_pres_ids']

	q="UPDATE `test_prescription` SET `status`='Forward' WHERE `test_pres_id`='%s'"%(test_pres_ids)
	id=update(q)
	if id>0:
		data['status'] = 'success'
		
	else:
		data['status'] = 'failed'
	data['method'] = 'user_forward_test_pres'
	return demjson.encode(data)



@api.route('/user_view_med_payment',methods=['get','post'])
def user_view_med_payment():
	data={}
	
	book_id=request.args['book_id']
	
	q="SELECT * FROM `payments` WHERE `book_id`='%s' AND `payment_type`='Medicne'"%(book_id)
	print(q)
	res = select(q)
	print(res)
	if res :
		data['status']  = 'success'
		data['data'] = res[0]['amount']
		data['data1'] = res[0]['pay_id']
		
	else:
		data['status']	= 'failed'
	data['method']='user_view_med_payment'
	return  demjson.encode(data)





@api.route('/user_view_test_payment',methods=['get','post'])
def user_view_test_payment():
	data={}
	
	book_id=request.args['book_id']
	
	q="SELECT * FROM `payments` WHERE `book_id`='%s' and payment_type='Lab' "%(book_id)
	print(q)
	res = select(q)
	print(res)
	if res :
		data['status']  = 'success'
		data['amount'] = res[0]['amount']
		data['pay_id']= res[0]['pay_id']
		
	else:
		data['status']	= 'failed'
	data['method']='user_view_test_payment'
	return  demjson.encode(data)



@api.route('/user_test_payment',methods=['get','post'])
def user_test_payment():

	data={}
	pay_id=request.args['pay_id']
	test_pres_ids=request.args['test_pres_ids']

	q="UPDATE `payments` SET `payment_date`=CURDATE() WHERE `pay_id`='%s'"%(pay_id)
	update(q)
	q="UPDATE`test_prescription` SET `status`='Paid' WHERE `test_pres_id`='%s'"%(test_pres_ids)
	update(q)
	
	data['status'] = 'success'
	
	data['method'] = 'user_test_payment'
	return demjson.encode(data)
 
 
 
 
@api.route('/chat', methods=['get', 'post'])
def chat():
    data = {}
    sender_id = request.args['sender_id']
    receiver_id = request.args['receiver_id']
    details = request.args['details']

    q2="INSERT INTO chat(sender_id,sender_type,receiver_id,receiver_type,message,`date_time`)VALUES('%s','user',(SELECT `login_id` FROM `doctors` WHERE `doctor_id`='%s'),'doctor','%s',NOW())"%(sender_id,receiver_id,details)
    id=insert(q2)
    if id > 0:
        data['status'] = 'success'

    else:
        data['status'] = 'failed'
    data['method'] = 'chat'
    return demjson.encode(data)


@api.route('/chatdetail', methods=['get', 'post'])
def chatdetail():
    data = {}

    sender_id = request.args['sender_id']
    receiver_id = request.args['receiver_id']


    q = "SELECT * FROM `chat` WHERE (`sender_id`='%s' AND `receiver_id`=(SELECT `login_id` FROM `doctors` WHERE `doctor_id`='%s')) OR (`sender_id`=(SELECT `login_id` FROM `doctors` WHERE `doctor_id`='%s') AND `receiver_id`='%s')"%(sender_id,receiver_id,receiver_id,sender_id)
    print(q)
    res = select(q)
    if res:
        data['status'] = 'success'
        data['data'] = res
    else:
        data['status'] = 'failed'
    data['method'] = 'chatdetail'
    return demjson.encode(data)

@api.route('/user_viewvaccination', methods=['get', 'post'])
def user_viewvaccination():
    data = {}
    q="select * from  vaccination"   
    res = select(q)
    if res:
        data['status'] = 'success'
        data['data'] = res
    else:
        data['status'] = 'failed'
    data['method'] = 'user_viewvaccination'
    return demjson.encode(data)


@api.route('/booknow', methods=['get', 'post'])
def booknow():
    data = {}
    login_id=request.args['login_id']
    amt=request.args['amt']
    vid=request.args['vid']

    q="insert into vaccinebooking values(null,'%s','%s',curdate(),(select user_id from users where login_id='%s'))"%(vid,amt,login_id)
    insert(q)
    data['status'] = 'success'

    return demjson.encode(data)



@api.route('/Viewvaccinebooking', methods=['get', 'post'])
def Viewvaccinebooking():
    data = {}
    q="SELECT * FROM  vaccinebooking INNER JOIN users USING (user_id) INNER JOIN vaccination USING (vaccination_id)"   
    res = select(q)
    if res:
        data['status'] = 'success'
        data['data'] = res
    else:
        data['status'] = 'failed'
    data['method'] = 'user_view_bookings'
    return demjson.encode(data)


@api.route('/usermakepayment')
def usermakepayment():
	data={}
	book_id=request.args['book_id']
	amts=request.args['amts']
	q="insert into payments values(null,'%s','%s',curdate(),'vaccinebooking',curtime())"%(book_id,amts)
	insert(q)
	data['status']="success"
	data['method']="user_payment"
	return str(data)


@api.route('/Userviewstaff', methods=['get', 'post'])
def Userviewstaff():
    data = {}
    q="SELECT * FROM  staff "   
    res = select(q)
    if res:
        data['status'] = 'success'
        data['data'] = res
    else:
        data['status'] = 'failed'
    data['method'] = 'Userviewstaff'
    return demjson.encode(data)


@api.route('/userbookstaff', methods=['get', 'post'])
def userbookstaff():
    data = {}
    login_id=request.args['login_id']
    sid=request.args['sid']
  

    q="insert into staffbooking values(null,'%s',curdate(),'booked',(select user_id from users where login_id='%s'))"%(sid,login_id)
    insert(q)
    data['status'] = 'success'

    return demjson.encode(data)


@api.route('/Viewstaffbooking')
def Viewstaffbooking():
	data={}
	login_id=request.args['login_id']
	q="select * from staffbooking  inner join staff using (staff_id) where  user_id=(select user_id from users where login_id='%s')"%(login_id)
	res=select(q)
	data['data']=res
	data['status']='success'
	data['method']="Viewstaffbooking"

	return demjson.encode(data) 



@api.route('/rate', methods=['get', 'post'])
def rate():
    data = {}
    rating=request.args['rating']
    log_id=request.args['log_id']
    review=request.args['review']
    bid=request.args['did']

  

    q="insert into rating values(null,'%s',curdate(),'%s','%s',(select user_id from users where login_id='%s'))"%(rating,review,bid,log_id)
    insert(q)
    data['status'] = 'success'

    return demjson.encode(data)


@api.route('/Viewproducts', methods=['get', 'post'])
def Viewproducts():
    data = {}
    q="select * from product  inner join shop using (shop_id)"
    res=select(q)
    data['data']=res
    
    data['status'] = 'success'

    return demjson.encode(data)


@api.route('/productbooking', methods=['get','post'])
def productbooking():
    data = {}
    login_id=request.args['login_id']
    # sid=request.args['sid']
    pid=request.args['pid']
    amt=request.args['amt']

    q="insert into productbook values(null,'%s','%s',curdate(),'booked',(select user_id from users where login_id='%s'))"%(pid,amt,login_id)
    insert(q)
    data['status'] = 'success'

    return demjson.encode(data)

@api.route('/ViewProductbooking', methods=['get','post'])
def ViewProductbooking():
    data = {}
    login_id=request.args['login_id']
  
    q="select * from productbook inner join product using (product_id) inner join shop using (shop_id) where user_id=(select user_id from users where login_id='%s')"%(login_id)
    res=select(q)
    data['data']=res

    
    data['status'] = 'success'

    return demjson.encode(data)
	







