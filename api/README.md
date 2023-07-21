# TODO

* Decorators (logs)
* Array literals
* Self types https://youtrack.jetbrains.com/issue/KT-6494

# Person collection

```
# PERSON COLLECTION SCHEMA
{
    "_id":{"$oid":"64a20e0c65e828693428a8c1"},
    "username":"username_0",
}

# CONTACT COLLECTION SCHEMA
{
    "_id":{"$oid":"64a20e0c65e828693428a8c5"},
    "person_id":{"$oid":"64a20e0c65e828693428a8c1"},      <--------- Person reference
    "data":"data_0",
}

# MESSAGE COLLECTION SCHEMA
{
    "_id":{"$oid":"64a278f583364c2f4f3c81c8"},
    "contact_receiver_id":{"$oid":"64a20e0c65e828693428a8c1"},     <------ Contact reference
    "contact_sender_id":{"$oid":"64a20e0c65e828693428a8d3"},       <------ Contact reference
}
```

# Lookup on person collection for specific person and all his contacts and messages

```
[
  {
    $match: {
      _id: ObjectId("64a20e0c65e828693428a8c1"),
    },
  },
  {
    $lookup: {
      from: "Contact",
      localField: "_id",
      foreignField: "person_id",
      as: "contacts",
      pipeline: [
         {
            $lookup: {
            from: "Messages",
            localField: "_id",
            foreignField: "contact_id",
            as: "messages",
         },
      ],
    },
  },
]
```

# Results

```
{
  "_id": { "$oid": "64a20e0c65e828693428a8c1" },
  "username": "username_0",
  "contacts": [
    {
      "_id": { "$oid": "64a20e0c65e828693428a8c5" },
      "person_id": { "$oid": "64a20e0c65e828693428a8c1" },
      "data": "data_0",
      "message: [
          {
            "_id": { "$oid": "64a20e0c65e828693428a8c5" },
            "contact_receiver_id":{"$oid":"64a20e0c65e828693428a8c1"},    
            "contact_sender_id":{"$oid":"64a20e0c65e828693428a8d3"},      
          },
          .......
      ]
    },
    .......
  ],
}
```

# What I want

```
{
  "user": {    <------- ALL USER INFORMATIONS WRAPED IN OBJECT "USER"
      "_id": { "$oid": "64a20e0c65e828693428a8c1" },
      "username": "username_0",
  },
  "contacts": [
    {
      "contact: { <------ ALL CONACT INFORMATIONS WRAPPED IN OBJECT "CONTACT"
          "_id": { "$oid": "64a20e0c65e828693428a8c5" },
          "person_id": { "$oid": "64a20e0c65e828693428a8c1" },
          "data": "data_0"
      },
      "message: [
          {
            "message: {  <-------- ALL MESSAGE INFORMATIONS WRAPPED IN OBJECT "MESSAGE
                "_id": { "$oid": "64a20e0c65e828693428a8c5" },
                "contact_receiver_id":{"$oid":"64a20e0c65e828693428a8c1"},
                "contact_sender_id":{"$oid":"64a20e0c65e828693428a8d3"},  
            }
          },
          .......
      ]
    },
    .......
  ],
}
```

# Motivation why I want this...

I use Kotlin Mongodb driver and to serialize data I want to use this data classes to get nested lookup results

```kotlin
@Serializable
data class DbMessages(
    val message: Message
)

@Serializable
data class DbContactsMessages(
    val contact: Contact,
    val messages: List<DbMessages>,
)

@Serializable
data class UserConctactsMessages(
    val person: Person,
    val contacts: List<DbContactsMessages>,
)
```
