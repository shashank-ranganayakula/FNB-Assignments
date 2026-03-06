-- Seed data for Student Management API
INSERT INTO students (first_name, last_name, email, date_of_birth, major, gpa, status, created_at, updated_at)
VALUES
  ('Alice',   'Johnson',  'alice.johnson@university.edu',  '2001-03-15', 'Computer Science',  3.85, 'ACTIVE',    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Bob',     'Smith',    'bob.smith@university.edu',      '2000-07-22', 'Mathematics',       3.20, 'ACTIVE',    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Carol',   'Williams', 'carol.w@university.edu',        '2002-11-05', 'Physics',           3.95, 'ACTIVE',    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('David',   'Brown',    'david.brown@university.edu',    '1999-01-30', 'Engineering',       2.75, 'INACTIVE',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Eva',     'Davis',    'eva.davis@university.edu',      '2001-09-18', 'Computer Science',  3.60, 'ACTIVE',    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Frank',   'Miller',   'frank.miller@university.edu',   '2000-06-12', 'Chemistry',         2.90, 'GRADUATED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
