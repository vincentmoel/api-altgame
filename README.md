<p align="center"><a href="https://www.binaracademy.com" target="_blank">
<img src="https://global-uploads.webflow.com/5e70b9a791ceb781b605048c/6152ae609d46491e37aa9af9_logo_binar-academy_horizontal_magenta_bg-transparan-p-500.png" width="300">
</a>
</p>
<p align="center"><a href="https://spring.io/projects/spring-boot" target="_blank">
<img src="https://spring.io/images/spring-logo-9146a4d3298760c2e7e49595184e1975.svg" width="400">
</a>
</p>
<p  align="center">
<img src="https://img.shields.io/badge/AltGame%20Version-1.0-green" alt="Latest Stable Version">
<img src="https://img.shields.io/badge/Spring%20Boot%20Version-2.6.8-green" alt="Latest Stable Version">
</p>

# Intelj Initialize

- ### Make  application-dev.properties
```
server.port=8080
spring.datasource.username=postgres
spring.datasource.password=
spring.datasource.url=jdbc:postgresql://localhost:5432/altgame

spring.jpa.hibernate.ddl-auto = update


spring.jpa.properties.hibernate.format_sql=true
logging.level.org.hibernate.SQL=DEBUG   
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
spring.mvc.pathmatch.matching-strategy = ANT_PATH_MATCHER
```
- ### Maven Command
` maven clean install `

# Requirements

- `Java 8`

# View Table

- vw_users

```roomsql
    CREATE VIEW vw_users AS
    SELECT 
        u.user_id, 
        u.username, 
        u.name, 
        u.email, 
        u.phone,
        u.bank_account,
        u.image,
        r.name AS role
    FROM 
        users u, 
        roles r
    WHERE u.role_id = r.role_id;
```
- vw_products
```roomsql
CREATE VIEW vw_products AS
    SELECT 
        p.product_id, 
        u.username, 
        c.name AS category, 
        p.name, 
        p.price,
        p.image,
        p.status,
        p.created_at,
		p.updated_at
    FROM 
        products p, 
        categories c,
		users u
    WHERE 
	p.category_id = c.category_id AND
	p.user_id = u.user_id
```
- vw_invoices
```roomsql
CREATE VIEW vw_invoices AS
SELECT 
	i.invoice_id, 
	i.no_invoice,
	i.address,
	i.status,
	i.created_at,
	i.updated_at, 
	b.price AS bid_price,
	(SELECT username FROM users WHERE users.user_id = b.user_id) AS buyer , 
	p.name, 
	p.price AS product_price, 
	p.image, 
	(SELECT username FROM users WHERE users.user_id = p.user_id) AS seller



FROM 
	invoices i,
	bids b,
	products p

WHERE 

i.bid_id = b.bid_id AND
b.product_id = p.product_id AND
b.status = 'accepted'
```

# Roles Table
```json
{
    "roleid"  : 1,
    "name"    : "buyer"
},
{
    "roleid"  : 2,
    "name"    : "seller"
}
```
# Categories Table
```json
{
  "categoryId"  : 1,
  "name"        : "Console",
  "created_at"  : "2022-06-24 00:00:00",
  "updated_at"  : "2022-06-24 00:00:00"
},
{
"categoryId"  : 2,
"name"        : "Video Game",
"created_at"  : "2022-06-24 00:00:00",
"updated_at"  : "2022-06-24 00:00:00"
},
{
"categoryId"  : 3,
"name"        : "Controller",
"created_at"  : "2022-06-24 00:00:00",
"updated_at"  : "2022-06-24 00:00:00"
},
{
"categoryId"  : 4,
"name"        : "Aksersoris",
"created_at"  : "2022-06-24 00:00:00",
"updated_at"  : "2022-06-24 00:00:00"
},
{
"categoryId"  : 5,
"name"        : "Board Game",
"created_at"  : "2022-06-24 00:00:00",
"updated_at"  : "2022-06-24 00:00:00"
},
{
"categoryId"  : 6,
"name"        : "Collectible",
"created_at"  : "2022-06-24 00:00:00",
"updated_at"  : "2022-06-24 00:00:00"
},
{
"categoryId"  : 7,
"name"        : "Other",
"created_at"  : "2022-06-24 00:00:00",
"updated_at"  : "2022-06-24 00:00:00"
}
```
