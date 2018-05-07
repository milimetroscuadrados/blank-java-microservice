variable "identifier" {
  description = "Identifier for your DB"
}

variable "storage" {
  default     = "100"
  description = "Storage size in GB"
}

variable "engine" {
  default     = "postgres"
  description = "Engine type, example values mysql, postgres"
}

variable "engine_version" {
  description = "Engine version"

  default = {
    mysql    = "5.6.22"
    postgres = "9.6.3"
  }
}

variable "instance_class" {
  description = "Instance class"
}

variable "db_name" {
  description = "db name"
}

variable "username" {
  description = "User name"
}

variable "password" {
  description = "password, provide through your ENV variables"
}

variable "cidr_blocks" {
  default     = "0.0.0.0/0"
  description = "CIDR for sg"
}

