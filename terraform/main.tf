terraform {
  backend "s3" {}
}

provider "aws" {
  region = "${var.aws_region}"
}

data "aws_ecs_cluster" "main" {
  cluster_name = "${var.ecs_name}"
}

data "aws_vpc" "main" {
  filter {
    name = "tag:Name"
    values = ["${var.vpc_name}"]
  }
}

data "template_file" "task_definition" {
  template = "${file("${path.module}/task-definition.json")}"

  vars {
    image_url        = "${var.image_url}"
    container_name   = "${var.container_name}"
    container_port   = "${var.container_port}"
    service_name     = "${var.service_name}"
    db_address       = "${aws_db_instance.default.address}"
    db_name          = "${var.db_name}"
    db_user          = "${var.username}"
    db_password      = "${var.password}"
    log_group_region = "${var.aws_region}"
    log_group_name   = "${aws_cloudwatch_log_group.app.name}"
  }
}

resource "aws_ecs_task_definition" "app" {
  family                = "${var.ecs_name}_${var.family}"
  container_definitions = "${data.template_file.task_definition.rendered}"
}

resource "aws_ecs_service" "app_svc" {
  name            = "${var.service_name}"
  cluster         = "${data.aws_ecs_cluster.main.id}"
  task_definition = "${aws_ecs_task_definition.app.arn}"
  desired_count   = 1
}


## CloudWatch Logs

resource "aws_cloudwatch_log_group" "app" {
  name = "${var.ecs_name}/${var.container_name}"

  tags = {
    env  = "${var.ecs_name}"
  }
}

