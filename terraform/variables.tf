variable "aws_region" {
  description = "The AWS region to create things in."
  default     = "us-east-1"
}

variable "ecs_name" {
  description = "Name of the ECS cluster"
}

variable "image_url" {
  description = "URL of the docker image"
}

variable "container_name" {
  description = "Name of the docker container"
}

variable "container_port" {
  description = "Port number of the container"
}

variable "family" {
  description = "Family name of the task"
}

variable "service_name" {
  description = "Name of service"
}

variable "vpc_name" {
  description = "Name of the VPC"
}

variable "environment" {
  description = "Enviroment name"
}
