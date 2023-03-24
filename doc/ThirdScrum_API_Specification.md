# The API Specification

## User

### Login

* Request
  * Method: Post
  * Url: /user/login
  * Body: User
* Response
  * Code
  * Message
  * Data: Map(String, String)--->("user_key": {user_key})
* Role
  * All
* Authority
  * None

### Logout

* Request
  * Method: Get
  * Url: /user/logout
  * Header: "user_key" : {user_key}
* Response
  * Code
  * Message
  * Data: Void
* Role
  * All
* Authority
  * None

### Register

* Request
  * Method: Post
  * Url: /user/create
  * Body: User
* Response
  * Code
  * Message
  * Data: Void
* Role
  * All
* Authority
  * user:create

### Delete user by user id

* Request
  * Method: Delete
  * Url: /user/delete/{userId}
  * Headers: "user_key" : {user_key}
* Response
  * Code
  * Message
  * Data: Void
* Role
  * Admin
* Authority
  * user:delete

### Update user

* Request
  * Method: Put
  * Url: /user/update
  * Headers: "user_key" : {user_key}
  * Body: User
* Response
  * Code
  * Message
  * Data: Void
* Role
  * All
* Authority
  * user:update:self

### Update user by user id

* Request
  * Method: Put
  * Url: /user/update/{userId}
  * Headers: "user_key" : {user_key}
  * Body: User
* Response
  * Code
  * Message
  * Data: Void
* Role
  * Admin
* Authority
  * user:update:other

### List all users

* Request
  * Method: Get
  * Url: /user/list
  * Header: "user_key" : {user_key}
* Response
  * Code
  * Message
  * Data: List(User)
* Role
  * Admin
* Authority
  * user:list

### View current user(profile)

* Request
  * Method: Get
  * Url: /user/view
  * Header: "user_key" : {user_key}
* Response
  * Code
  * Message
  * Data: User
* Role
  * All
* Authority
  * user:view

## Building

### Create building

* Request
  * Method: Post
  * Url: /building/create
  * Headers: "user_key" : {user_key}
  * Body: Building
* Response
  * Code
  * Message
  * Data: Void
* Role
  * Admin
  * Manager
* Authority
  * building:create

### Delete building

* Request
  * Method: Delete
  * Url: /building/delete/{buildingId}
  * Headers: "user_key" : {user_key}
* Response
  * Code
  * Message
  * Data: Void
* Role
  * Admin
  * Manager
* Authority
  * building:delete

### Update building by building id

* Request
  * Method: Put
  * Url: /building/update/{buildingId}
  * Headers: "user_key" : {user_key}
  * Body: Building
* Response
  * Code
  * Message
  * Data: Void
* Role
  * Admin
  * Manager
* Authority
  * building:update

### List all buildings

* Request
  * Method: Get
  * Url: /building/list
  * Headers: "user_key" : {user_key}
* Response
  * Code
  * Message
  * Data: List(Building)
* Role
  * All
* Authority
  * building:list

## Flat

### Create flat by building id

* Request
  * Method: Post
  * Url: /flat/create/{buildingId}
  * Headers: "user_key" : {user_key}
  * Body: Flat
* Response
  * Code
  * Message
  * Data: Void
* Role
  * Admin
  * Manager
* Authority
  * flat:create

### Delete flat by flat id

* Request
  * Method: Delete
  * Url: /flat/delete/{flatId}
  * Headers: "user_key" : {user_key}
* Response
  * Code
  * Message
  * Data: Void
* Role
  * Admin
  * Manager
* Authority
  * flat:delete

### Update flat by flat id

* Request
  * Method: Put
  * Url: /flat/update/{flatId}
  * Headers: "user_key" : {user_key}
  * Body: Flat
* Response
  * Code
  * Message
  * Data: Void
* Role
  * Admin
  * Manager
* Authority
  * flat:update

### List all flats

* Request
  * Method: Get
  * Url: /flat/list
  * Headers: "user_key" : {user_key}
* Response
  * Code
  * Message
  * Data: List(Flat)
* Role
  * All
* Authority
  * flat:list

### List all flats by building id

* Request
  * Method: Get
  * Url: /flat/list/{buildingId}
  * Headers: "user_key" : {user_key}
* Response
  * Code
  * Message
  * Data: List(Flat)
* Role
  * All
* Authority
  * flat:list

## Role

### Create role

* Request
  * Method: Post
  * Url: /role/create
  * Headers: "user_key" : {user_key}
  * Body: Role
* Response
  * Code
  * Message
  * Data: Void
* Role
  * Admin
* Authority
  * role:create

### Delete role by role id

* Request
  * Method: Delete
  * Url: /role/delete/{roleId}
  * Headers: "user_key" : {user_key}
* Response
  * Code
  * Message
  * Data: Void
* Role
  * Admin
* Authority
  * role:delete

### Update role by role id

* Request
  * Method: Put
  * Url: /role/update/{roleId}
  * Headers: "user_key" : {user_key}
  * Body: Role
* Response
  * Code
  * Message
  * Data: Void
* Role
  * Admin
* Authority
  * role:update

### List all roles

* Request
  * Method: Get
  * Url: /role/list
  * Headers: "user_key" : {user_key}
* Response
  * Code
  * Message
  * Data: List(Role)
* Role
  * Admin
* Authority
  * role:list

## Permission

### Create permission

* Request
  * Method: Post
  * Url: /permission/create
  * Headers: "user_key" : {user_key}
  * Body: Permission
* Response
  * Code
  * Message
  * Data: Void
* Role
  * Admin
* Authority
  * permission:create

### Delete permission by permission id

* Request
  * Method: Delete
  * Url: /permission/delete/{permissionId}
  * Headers: "user_key" : {user_key}
* Response
  * Code
  * Message
  * Data: Void
* Role
  * Admin
* Authority
  * permission:delete

### Update permission by permission id

* Request
  * Method: Put
  * Url: /permission/update/{permissionId}
  * Headers: "user_key" : {user_key}
  * Body: Permission
* Response
  * Code
  * Message
  * Data: Void
* Role
  * Admin
* Authority
  * permission:update

### List all permissions

* Request
  * Method: Get
  * Url: /permission/list
  * Headers: "user_key" : {user_key}
* Response
  * Code
  * Message
  * Data: List(Permission)
* Role
  * Admin
* Authority
  * permission:list

## UserRole

### Create role for user by user id and role id

* Request
  * Method: Post
  * Url: /user/role/{userId}/{roleId}
  * Headers: "user_key" : {user_key}
* Response
  * Code
  * Message
  * Data: Void
* Role
  * Admin
* Authority
  * user:role:create

### Delete role for user by user id and role id

* Request
  * Method: Delete
  * Url: /user/role/{userId}/{roleId}
  * Headers: "user_key" : {user_key}
* Response
  * Code
  * Message
  * Data: Void
* Role
  * Admin
* Authority
  * user:role:delete

### List all roles for user by user id

* Request
  * Method: Get
  * Url: /user/role/list/{userId}
  * Headers: "user_key" : {user_key}
* Response
  * Code
  * Message
  * Data: List(Role)
* Role
  * Admin
* Authority
  * user:role:list



## RolePermission

### Create permission for role by role id and permission id

* Request
  * Method: Post
  * Url: /role/permission/{roleId}/{permissionId}
  * Headers: "user_key" : {user_key}
* Response
  * Code
  * Message
  * Data: Void
* Role
  * Admin
* Authority
  * role:permission:create

### Delete permission for role by role id and permission id

* Request
  * Method: Delete
  * Url: /role/permission/{roleId}/{permissionId}
  * Headers: "user_key" : {user_key}
* Response
  * Code
  * Message
  * Data: Void
* Role
  * Admin
* Authority
  * role:permission:delete

### List all permissions for role by role id

* Request
  * Method: Get
  * Url: /role/permission/list/{roleId}
  * Headers: "user_key" : {user_key}
* Response
  * Code
  * Message
  * Data: List(Permission)
* Role
  * Admin
* Authority
  * role:permission:list

### List all permissions for user by user id

* Request
  * Method: Get
  * Url: /user/permission/list/{userId}
  * Headers: "user_key" : {user_key}
* Response
  * Code
  * Message
  * Data: List(Permission)
* Role
  * Admin
* Authority
  * user:permission:list

### List all users' permissions

* Request
  * Method: Get
  * Url: /user/permission/list
  * Headers: "user_key" : {user_key}
* Response
  * Code
  * Message
  * Data: List(UserPermission)
* Role
  * Admin
* Authority
  * user:permission:list

