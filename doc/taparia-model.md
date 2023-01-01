# Taparia Model

## User
Statements:
- Information about our client is mapped to entity called user. 
- User can have its set of permissions: for example read, write some resource.
- It has a unique identifier - key - and all actions are connected with this id.
- Users have its own login and password. 
- Login is a string with pattern `^[A-Za-z0-9]{4,32}$`
- Password is a string with pattern `^[A-Za-z0-9]{8,32}$` 
```java
class UserAccount {
    Id id;
    Name name;
    Password password;
    
    class Id { long value; }
    
    class Name { String value; }
    
    class Password {
        byte[] hash;
        byte[] salt;
        HashAlgorithm hashAlgorithm;
        
        enum HashAlgorithm {
            SHA_256("SHA-256") 
        }
    }
}
```

## Picture
Statements:
- Picture is a resource, that user creates.
- Picture has its own id.
- It is connected with users by special UserPicturePermission table.
- It is constant what means that user can't change it.
- User can only delete a picture, and create it.
- Picture has its name and owner.
- A pair (owner, picture_name) is unique.
- A picture name is a string with pattern `^[A-Za-z0-9]{4,64}$`
- Picture data is a figure stored as json string.
```java
class Picture {
    Id id;
    UserAccount.Id ownerId;
    Name name;
    Data data;
    
    class Id { long value; } 
    
    class Name { String value; }
    
    class Data { Figure figure; }
}
```

## UserPicturePermission
- It is a bigraph G = (U, P, E), where
- U - users
- P - pictures
- E - relation between user and picture with type of the relation.
```
Example 1:
User u can view content of picture p 
if and only if 
    there are exists an edge e in E such that 
        e.ownerId == u.id and
        e.type == 'READ' and
        e.pictureId == p.id
``` 
```java
class UserPicturePermission {
    UserAccount.Id ownerId;
    Type type;
    Picture.Id pictureId;
    
    enum Type { READ, DELETE }
}
```
