# User
* user_id bigint auto_increment (p_key)
* user_email varchar not_null
* User_password varchar not_null
* user_name varchar not_null
* user_phone varchar
* user_address varchar

# Role

* role_id bigint auto_increment (p_key)
* role_name varchar not_null
* role_key varchar not_null

# Permission

* permission_id bigint auto_increment (p_key)
* permission_name varchar not_null
* permission_key varchar not_null

# User_Role
* id bigint auto_increment (p_key)
* user_id bigint not_null
* role_id bigint not_null 

# Role_Permission

* id bigint auto_increment (p_key)
* role_id bigint not_null
* permission_id bigint not_null

# Building

* building_id bigint auto_increment (p_key)
* building_name varchar not_null
* building_address varchar not_null

# Flat
* flat_id bigint auto_increment (p_key)
* building_id bigint not_null
* user_id bigint
* flat_name varchar not_null
* flat_area decimal not_null
* flat_sold_out_date datetime
* flat_price decimal
