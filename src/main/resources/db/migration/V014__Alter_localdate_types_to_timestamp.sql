ALTER TABLE polls
    ALTER COLUMN create_at TYPE timestamp,
    ALTER COLUMN create_at SET DEFAULT current_timestamp,
    ALTER COLUMN up_to_date TYPE timestamp;

ALTER TABLE votes
    ALTER COLUMN create_at TYPE timestamp,
    ALTER COLUMN create_at SET DEFAULT current_timestamp;