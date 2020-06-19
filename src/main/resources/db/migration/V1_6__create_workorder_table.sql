create table if not exists workorder
(
    id serial primary key,
    title varchar(255),
    description text,
    status varchar(255),
    team_id int,
    organization_id int not null,
    created_at timestamp default current_timestamp,
    created_by int,
    updated_at timestamp default current_timestamp,
    updated_by int,
    constraint fk_team__workorder foreign key (team_id) references team(id),
    constraint fk_organization__workorder foreign key (organization_id) references organization(id),
    constraint fk_user__workorder foreign key (updated_by) references application_user(id),
    constraint fk_user__workorder2 foreign key (created_by) references application_user(id)
);

CREATE OR REPLACE FUNCTION update_updatedAt_column()
RETURNS TRIGGER AS $$
BEGIN
   IF row(NEW.*) IS DISTINCT FROM row(OLD.*) THEN
      NEW.updated_at = now();
      RETURN NEW;
   ELSE
      RETURN OLD;
   END IF;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_workorder_modtime BEFORE UPDATE ON workorder FOR EACH ROW EXECUTE PROCEDURE  update_updatedAt_column();
