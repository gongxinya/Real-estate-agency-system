# User
* user_id (p_key)
* user_name
* user_phone
* user_email
* user_address
* user_position(admin, manager, customer)

# Permission
* permission_id
* permission_url

# User_Permission
* user_id
* peimission_id

# Building
* building_id (p_key)
* building_name
* building_address

# Flat
* flat_id (p_key)
* building_id (p_key, f_key)
* flat_name
* user_id (f_key)
* flat_area
* flat_sold_out_date
* flat_price
