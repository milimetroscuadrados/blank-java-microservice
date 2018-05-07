#DB
resource "aws_db_instance" "default" {
  identifier             = "${var.identifier}"
  allocated_storage      = "${var.storage}"
  engine                 = "${var.engine}"
  engine_version         = "${lookup(var.engine_version, var.engine)}"
  instance_class         = "${var.instance_class}"
  name                   = "${var.db_name}"
  username               = "${var.username}"
  password               = "${var.password}"
  vpc_security_group_ids = ["${data.aws_security_group.ecs.id}"]
  db_subnet_group_name   = "${var.ecs_name}-main-subnet-group"
  publicly_accessible    = false
  monitoring_interval    = 60
  monitoring_role_arn    = "${data.aws_iam_role.rds.arn}"
  multi_az               = "${var.environment == "prod" ? true : false}"
  maintenance_window     = "sun:02:30-sun:03:00"
  backup_window          =  "03:15-03:45"
  backup_retention_period = 7
  storage_type           = "gp2"

}

data "aws_iam_role" "rds" {
  role_name = "${var.ecs_name}-rds-monitoring-role"
}

#Security
data "aws_security_group" "ecs"{
  filter{
    name = "group-name",
    values = ["${var.ecs_name}-main-rds-sg"]
  }
}

data "aws_instance" "bastion" {
  filter {
    name   = "tag:Name"
    values = ["${var.ecs_name}-bastion"]
  }
}


