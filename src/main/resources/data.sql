/*
--Sample data for table posts

INSERT INTO `posts` (`content`, `description`, `title`) VALUES
('Spring Boot simplifies Java development with its auto-configuration and starter dependencies.', 'Introduction to Spring Boot framework.', 'Getting Started with Spring Boot'),
('Java Streams allow for functional-style operations on collections of objects.', 'A guide to Java Stream API basics.', 'Understanding Java Streams'),
('Docker helps in containerizing applications for consistent deployment.', 'Overview of Docker in modern DevOps.', 'Why Use Docker for Development'),
('RESTful APIs are a common architectural style for web services.', 'Basics of building RESTful APIs.', 'Creating Your First REST API'),
('Hibernate ORM simplifies database interactions in Java applications.', 'A beginners guide to Hibernate ORM.', 'Using Hibernate with Java'),
('Thymeleaf is a modern server-side Java template engine.', 'Intro to Thymeleaf for building dynamic views.', 'Building Web UIs with Thymeleaf'),
('Spring Security handles authentication and authorization in Spring apps.', 'Exploring the features of Spring Security.', 'Securing Applications with Spring Security'),
('JUnit is a widely used testing framework for Java.', 'Learn how to write unit tests in Java.', 'Unit Testing with JUnit'),
('React is a powerful JavaScript library for building UIs.', 'Why React is popular for frontend development.', 'Introduction to React for Java Developers'),
('Kafka is a distributed streaming platform for building real-time data pipelines.', 'What is Apache Kafka and how it works.', 'Real-Time Data with Apache Kafka');


--Sample data for table comments

INSERT INTO `comments` (`body`, `email`, `name`, `post_id`) VALUES
-- Comments for Post ID 1 (Getting Started with Spring Boot)
('Spring Boot made my project setup so much easier. Excellent article!', 'john.doe@example.com', 'John Doe', 1),
('I love how Spring Boot minimizes boilerplate code. Great post!', 'jane.smith@example.com', 'Jane Smith', 1),
('Great guide for beginners! Now I feel more confident with Spring Boot.', 'samuel.brown@example.com', 'Samuel Brown', 1),

-- Comments for Post ID 2 (Understanding Java Streams)
('Java Streams really make working with collections so much cleaner. Great explanation!', 'michael.jones@example.com', 'Michael Jones', 2),
('Thanks for the deep dive into Streams. I learned a lot about the map function!', 'lucy.white@example.com', 'Lucy White', 2),
('Very helpful for anyone new to the Java Stream API. Keep it up!', 'andrew.taylor@example.com', 'Andrew Taylor', 2),

-- Comments for Post ID 3 (Why Use Docker for Development)
('Docker has been a game changer for me in terms of deployment. Thanks for this article!', 'katherine.green@example.com', 'Katherine Green', 3),
('I had no idea how easy it was to use Docker. Your post made it clear and easy to follow.', 'steven.peters@example.com', 'Steven Peters', 3),
('Docker is essential for scaling applications. This post helped me get started quickly.', 'nancy.james@example.com', 'Nancy James', 3),

-- Comments for Post ID 4 (Creating Your First REST API)
('I was struggling with creating REST APIs in Spring, and this post cleared everything up!', 'paul.martin@example.com', 'Paul Martin', 4),
('Nice breakdown of REST principles. I\'ll definitely be referring to this guide.', 'lisa.rodgers@example.com', 'Lisa Rodgers', 4),
('Great post! I will definitely implement REST API in my next project.', 'timothy.foster@example.com', 'Timothy Foster', 4),

-- Comments for Post ID 5 (Using Hibernate with Java)
('Hibernate really simplifies database management in Java. Great post for beginners!', 'anna.harris@example.com', 'Anna Harris', 5),
('Thanks for this guide! Hibernate has been a challenge for me, but this makes it easier to understand.', 'jason.clark@example.com', 'Jason Clark', 5),
('I didn’t realize how much Hibernate automates. Looking forward to using it more in my apps.', 'olivia.moore@example.com', 'Olivia Moore', 5),

-- Comments for Post ID 6 (Building Web UIs with Thymeleaf)
('Thymeleaf is a great choice for Java web apps. Your tutorial was very easy to follow.', 'gregory.daniel@example.com', 'Gregory Daniel', 6),
('This is a great introduction to Thymeleaf. I will definitely be using it in my Spring apps.', 'grace.howard@example.com', 'Grace Howard', 6),
('I always struggled with JSP, but Thymeleaf seems much simpler and more flexible. Thanks for the post!', 'victor.evans@example.com', 'Victor Evans', 6),

-- Comments for Post ID 7 (Securing Applications with Spring Security)
('Spring Security is a must-have for securing Spring-based apps. Thanks for the detailed guide!', 'harry.williams@example.com', 'Harry Williams', 7),
('I learned a lot about JWT tokens from this post. Very useful!', 'sophie.garcia@example.com', 'Sophie Garcia', 7),
('Security is often overlooked in apps, but this post made me realize how important it is. Great job!', 'joseph.morris@example.com', 'Joseph Morris', 7),

-- Comments for Post ID 8 (Unit Testing with JUnit)
('JUnit is essential for testing Java applications. I appreciate the clear examples in this post!', 'nina.kelly@example.com', 'Nina Kelly', 8),
('Unit testing is something I always struggled with. This guide gave me a lot of insight into writing good tests.', 'ryan.davis@example.com', 'Ryan Davis', 8),
('I\'m going to start writing more unit tests now. Your post made it seem so simple!', 'james.baker@example.com', 'James Baker', 8),

-- Comments for Post ID 9 (Introduction to React for Java Developers)
('React is the future of front-end development. This was a great introduction for Java developers!', 'lucas.martin@example.com', 'Lucas Martin', 9),
('As a Java developer, I struggled to get into React. This post made it much easier to understand.', 'grace.johnson@example.com', 'Grace Johnson', 9),
('I’ve been hesitant to learn React, but now I’m excited to give it a try! Thanks for this post.', 'emma.wilson@example.com', 'Emma Wilson', 9),

-- Comments for Post ID 10 (Real-Time Data with Apache Kafka)
('Kafka is powerful for real-time streaming. This guide is perfect for getting started with it.', 'ethan.smith@example.com', 'Ethan Smith', 10),
('I always wanted to learn about Kafka, and this post made it so much easier to grasp the basics.', 'madeline.lee@example.com', 'Madeline Lee', 10),
('Real-time data processing is essential, and Kafka is one of the best tools for the job. Great post!', 'oliver.king@example.com', 'Oliver King', 10);


*/