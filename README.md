## Note

This is no longer maintained. Record keeping purpose.

## Overview

It monitors a db `couch_db` in `config.ini` and when new document is created, it will send the doc to rabbitmq.

At the same time, this has a rabbitmq consumer and when there's a new message comes up in the queue, it retrieves it and send email via AWS.

After it sends an email, it deletes the email document from the db.

## Prerequisites

- RabbitMQ (3.4.4)
- Couchdb (1.3.1 and 1.6.1)
- AWS account (to send emails / Amazon SES SMTP)

## Getting Started

- Copy and paste `config.ini.sample` to `config.ini`
- Update related values
- Compile and run