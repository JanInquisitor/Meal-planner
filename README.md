Sure! Here's a sample README file based on the provided input and output examples:

# Meal Planner Application

This is a command-line application that allows you to manage and plan your meals. You can add meals, view meals by category, plan meals for specific days, and save your meal plans.

## Prerequisites

- Java Development Kit (JDK) 8 or later
- Command-line interface or terminal

## Getting Started

1. Clone the repository:

```shell
git clone https://github.com/your-username/meal-planner.git
```

2. Compile the source code:

```shell
javac Main.java
```

3. Run the application:

```shell
java Main
```

## Usage

The application provides the following options:

- `add`: Add a new meal to the database.
- `show`: Display meals from a specific category.
- `plan`: Plan meals for each day of the week.
- `save`: Save the planned meals to a file.
- `exit`: Exit the application.

### Example 1

```plaintext
What would you like to do (add, show, exit)?
> add
Which meal do you want to add (breakfast, lunch, dinner)?
> lunch
Input the meal's name:
> salad
Input the ingredients:
> lettuce, tomato, onion, cheese, olives
The meal has been added!
What would you like to do (add, show, exit)?
> add
Which meal do you want to add (breakfast, lunch, dinner)?
> lunch
Input the meal's name:
> omelette
Input the ingredients:
> eggs, milk, cheese
The meal has been added!
What would you like to do (add, show, exit)?
> add
Which meal do you want to add (breakfast, lunch, dinner)?
> breakfast
Input the meal's name:
> oatmeal
Input the ingredients:
> oats, milk, banana, peanut butter
The meal has been added!
What would you like to do (add, show, exit)?
> show
Which category do you want to print (breakfast, lunch, dinner)?
> breakfast
Category: breakfast
Name: oatmeal
Ingredients:
oats
milk
banana
peanut butter
What would you like to do (add, show, exit)?
> show
Which category do you want to print (breakfast, lunch, dinner)?
> lunch
Category: lunch

Name: salad
Ingredients:
lettuce
tomato
onion
cheese
olives

Name: omelette
Ingredients:
eggs
milk
cheese

What would you like to do (add, show, exit)?
> exit
Bye!
```

### Example 2

```plaintext
What would you like to do (add, show, plan, exit)?
> plan
Monday
oatmeal
sandwich
scrambled eggs
yogurt
Choose the breakfast for Monday from the list above:
> yogurt
avocado egg salad
chicken salad
sushi
tomato salad
wraps
Choose the lunch for Monday from the list above:
> tomato salad
beef with broccoli
pesto chicken
pizza
ramen
tomato soup
Choose the dinner for Monday from the list above:
> spaghetti
This meal doesn’t exist. Choose a meal from the list above.
> ramen
Yeah! We planned the meals for Monday.

Tuesday
oatmeal
sandwich
scrambled eggs
yogurt
Choose the breakfast for Tuesday from the list above:
> oatmeal
avocado egg salad
chicken salad
sushi
tomato salad
wraps


Choose the lunch for Tuesday from the list above:
> wraps
beef with broccoli
pesto chicken
pizza
ramen
tomato soup
Choose the dinner for Tuesday from the list above:
> ramen
Yeah! We planned the meals for Tuesday.

Wednesday
oatmeal
sandwich
scrambled eggs
yogurt
Choose the breakfast for Wednesday from the list above:
> sandwich
avocado egg salad
chicken salad
sushi
tomato salad
wraps
Choose the lunch for Wednesday from the list above:
> avocado egg salad
beef with broccoli
pesto chicken
pizza
ramen
tomato soup
Choose the dinner for Wednesday from the list above:
> pesto chicken
Yeah! We planned the meals for Wednesday.

Thursday
oatmeal
sandwich
scrambled eggs
yogurt
Choose the breakfast for Thursday from the list above:
> oatmeal
avocado egg salad
chicken salad
sushi
tomato salad
wraps
Choose the lunch for Thursday from the list above:
> chicken salad
beef with broccoli
pesto chicken
pizza
ramen
tomato soup
Choose the dinner for Thursday from the list above:
> tomato soup
Yeah! We planned the meals for Thursday.

Friday
oatmeal
sandwich
scrambled eggs
yogurt
Choose the breakfast for Friday from the list above:
> yogurt
avocado egg salad
chicken salad
sushi
tomato salad
wraps
Choose the lunch for Friday from the list above:
> sushi
beef with broccoli
pesto chicken
pizza
ramen
tomato soup
Choose the dinner for Friday from the list above:
> pizza
Yeah! We planned the meals for Friday.

Saturday
oatmeal
sandwich
scrambled eggs
yogurt
Choose the breakfast for Saturday from the list above:
> scrambled eggs
avocado egg salad
chicken salad
sushi
tomato salad
wraps
Choose the lunch for Saturday from the list above:
> wraps
beef with broccoli
pesto chicken
pizza
ramen
tomato soup
Choose the dinner for Saturday from the list above:
> pesto chicken
Yeah! We planned the meals for Saturday.

Sunday
oatmeal
sandwich
scrambled eggs
yogurt
Choose the breakfast for Sunday from the list above:
> scrambled eggs
avocado egg salad
chicken salad
sushi
tomato salad
wraps
Choose the lunch for Sunday from the list above:
> tomato salad
beef with broccoli
pesto chicken
pizza
ramen
tomato soup
Choose the dinner for Sunday from the list above:
> beef with broccoli
Yeah! We planned the meals for Sunday.

Monday
Breakfast: yogurt
Lunch: tomato salad
Dinner: ramen

Tuesday
Breakfast: oatmeal
Lunch: wraps
Dinner: ramen

Wednesday
Breakfast: sandwich
Lunch: avocado egg salad
Dinner: pesto chicken

Thursday
Breakfast: oatmeal
Lunch: chicken salad
Dinner: tomato soup

Friday
Breakfast: yogurt
Lunch: sushi
Dinner: pizza

Saturday
Breakfast: scrambled eggs
Lunch: wraps
Dinner: pesto chicken

Sunday
Breakfast: scrambled eggs
Lunch: tomato salad
Dinner: beef with broccoli

What would you like to do (add, show, plan, exit)?
> exit
Bye!
```