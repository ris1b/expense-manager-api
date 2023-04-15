"# expense-manager-api" 

-- RESTFUL API for managing expenses
	-- Users can Resgister, Login and then record their expense transactions
	under different categories.

	-- loggedin user can
		-- Create Category for
			-- shopping 
				-- transactions
			-- food items
				-- transactions
			-- house hold billings
				-- HH transaction
	so logged in user
	-- create category
		-- add Transaction (for that category)
	[1 : M] :: User can have multiple category, each category can have 
	multiple transactions.

ENDSPOINTS:
POST /register
``````````
{
	"firstName": "Rishabh"
	"lastName": "Jiwani"
	"email": "rishabh@gmail.com"
	"password": "test123"
}
``````````
Response: JWT Token  (We can set the Expiry date of this Token)
````````````````````
{
	"token":
		"JWT-token................
		........................."
}
````````````````````



POST /login: existing user can login
``````````
{
	"email": “rishab@gmail.com”
	“password”: “test123”
}
``````````
Response: Here also we will get a JWT Token
``````````
{
	"token":
		"JWT-token................
		........................"
}
``````````

ALL THE REMAINING END-POINTS ARE PROTECTED WHICH ARE RELATED TO VARIOUS
OPERATIONS ON CATEGORIES AND TRANSACTIONS.
THEY ARE ALSO RELATED TO SPECIFIC USER.

Therefore, for these remaining end-points we need to pass this Token as
Authorization Header.



POST /categories: For creating a new category
``````````
{
	“title”: “Shopping”
	“description”: “This is for recording all my shopping transactions”
}
``````````
Response: 403:Forbidden, Authorization Header must be present


>> Go to Header
>> Key
>> Key: Authorization
>> Value: Bearer <Token>

Now,

POST /categories: For creating a new category
``````````
{
	“title”: “Shopping”
	“description”: “This is for recording all my shopping transactions”
}
``````````
Response: 201 Created
``````````
{
	“categoryID”: 1,
	“userId”: 1,
	“title”: “Shopping”,
	“description”: “this is for recording all my shopping transactions”,
	“totalExpense”: 0.0
}
``````````



GET /categories: returns array of all categories
Request
Provide AuthHeader
``````````
Key: Authorization 	Value: Bearer <Token>
``````````

Response:
`````
{
	“categoryID”: 1,
	“userId”: 1,
	“title”: “Shopping”,
	“description”: “this is for recording all my shopping transactions”,
	“totalExpense”: 0.0
}
`````



GET /categories/1 :get category by ID
It returns a Single JSON Object

Request:
Provide AuthHeader
``````````
Key: Authorization 	Value: Bearer <Token>
``````````
Response:
`````
{
	“categoryID”: 1,
	“userId”: 1,
	“title”: “Shopping”,
	“description”: “this is for recording all my shopping transactions”,
	“totalExpense”: 0.0
}
`````



PUT /categories/1
For updating the category
Request:
Provide AuthHeader
``````````
Key: Authorization 	Value: Bearer <Token>
``````````
Body:
``````````
{
	“title”: “Shopping”
	“description”: “Shopping Transactions”
}
``````````
Response:
``````````
{
	“success”: true
}
``````````



POST /categories/1/transactions
Adding a Transaction to a category.
Note: All operations on a Transaction are for a particular categort only.
Provide:
``````````
Key: Authorization 	Value: Bearer <Token>
``````````
Body: As payload, we will send amount of Transaction.
``````````
{
	“amount”: 10000,
	“note”: “shopping for new year”,
	“transactionDate”: 1577817000000
}
``````````

Response:
``````````
{
	“transactionId”: 10000,
	“categoryId”: 1,
	“userId”: “1”,
	“amount”: 10000.0,
	“note”: “shopping for new year”,
	“transactionDate”: 1577817000000
}
``````````



GET /categories/1/transactions
Provide AuthHeader
``````````
Key: Authorization 	Value: Bearer <Token>
``````````
Response: Array containing all transaction of Shopping Category
``````````
[
	{

		“transactionID”: 1,
		“categoryId”: 1,
		“userId”: 1,
		“amount”: 10000.0,
		“note” “shopping for new year”,
		“transactionDate”: 1557781700000
	}
]
``````````



GET /categories/1/transactions/1000 (get transaction by Id)
To get single Transaction object
Provide AuthHeader
``````````
Key: Authorization 	Value: Bearer <Token>
``````````
Response
``````````
{
	“transactionID”: 1,
	“categoryId”: 1,
	“userId”: 1,
	“amount”: 10000.0,
	“note” “shopping for new year”,
	“transactionDate”: 1557781700000
}
``````````




PUT /categories/1/transactions/100
Provide AuthHeader
``````````
Key: Authorization 	Value: Bearer <Token>
``````````
Body:
``````````
{
	“amount”: 15000,
	“note”: “new year shopping”,
	“transactionDate”: 1577817000000
}
``````````

Response
``````````
{
	“success”:true
}
``````````




GET /categories/1/transactions/100
Response:
``````````
{

	“transactionID”: 1000,
	“categoryId”: 1,
	“userId”: 1,
	“amount”: 15000.0,
	“note” “new year shopping”,
	“transactionDate”: 1577817000000
	}
``````````




DELETE /categories/1/transactions/100
Provide AuthHeader
``````````
Key: Authorization 	Value: Bearer <Token>
``````````
Response:
``````````
{
	“success”:true
}
``````````


// delete category with all transactions
// Note: each category can have many transactions --> CASCADE DELETE
// # First Delete all transactions of this {id}
// # Then delete the Category itself


DELETE /categories/1
Provide AuthHeader
``````````
Key: Authorization 	Value: Bearer <Token>
``````````

Repsonse:
``````````
{
	“success”: true
}
``````````
