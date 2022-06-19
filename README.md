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

# Requirements

- `Java 8`

# View Table

- vw_users

```
    CREATE VIEW vw_users AS
    SELECT 
        u.user_id, 
        u.username, 
        u.password,
        u.name, 
        u.email, 
        u.phone,
        u.bank_account,
        u.image,
        r.name AS role
    FROM 
        users AS u, 
        roles AS r
    WHERE u.role_id = r.role_id;
```

# Roles Table
```json
{
    "roleid":1,
    "name" : "buyer"
},
{
    "roleid":2,
    "name" : "seller"
},
```
