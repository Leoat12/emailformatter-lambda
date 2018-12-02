# Email Formatter

![alt text](./diagram.png "Application Diagram")

The **Email Formatter** stack has the object of being a 
quick and decoupled way of sending emails from various applications you may have. 
It is not uncommon for web applications the need to send emails for users 
and administrators for many reasons. These emails usually has a template, 
which is saved in a readily available location. Those templates have placeholders 
where information about the user and business you be put. 

Sending emails is a task that can take a few precious milliseconds while the user
is waiting for the response, so it is always good to decouple this task and do it 
asynchronously. This stack aims to do just that. It uses SQS to queue the email 
information to be send. A Lambda function will trigger automatically whenever 
there is any message in the configured queue. The lambda function will prepare the
email with the information from the SQS message and then send it through SES to the
recipient. The function uses **Apache Freemarker** as template engine and the email
template must use Freemarker syntax to add placeholders. 

**More template engines are planned to be added in the future as well more sending
email services to make the function customizable.**

*Any help improving the stack is welcome!*
