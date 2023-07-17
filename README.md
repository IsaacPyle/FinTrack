# FinTrack Spring Boot Application

A spring boot application that tracks Users, Budgets, and Transactions, using a RESTful API.
This will have a frontend component in the future, but for now is just a proof of concept.

## About

This is an API that handles requests and delivers responses for a financial tracking application. This application can have Users (No authentication yet), which own budgets. 
Budgets will have more in the future, but for now the idea is that they have categories that have an allocated amount of spending based on the user's preference,
and then transactions that are added in to monitor progress towards those categories.

## Roadmap

I am planning on many changes in the near future. 
My ideas are:

* Finish implementing transactions
* Add tests around transactions
* Update budgets to use categories
* Add user controller endpoint to update budget category limits

## Authors

Isaac Pyle (IsaacPyle)