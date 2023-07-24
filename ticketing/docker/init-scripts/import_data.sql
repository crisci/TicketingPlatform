-- Check if customers table is empty
DO $$
BEGIN
  RAISE NOTICE 'Script init';
  IF (SELECT COUNT(*) FROM customers) = 0 THEN
    -- Print a message to the console
    RAISE NOTICE 'Inserting data into customers table...';
    
    -- Insert data into customers table
    COPY customers FROM '/var/lib/postgresql/data/imports/customers.csv' DELIMITER ',' CSV HEADER;
    
    -- Print a message to the console
    RAISE NOTICE 'Data inserted into customers table.';
  END IF;
END $$;

-- Check if employees table is empty
DO $$
BEGIN
  IF (SELECT COUNT(*) FROM employees) = 0 THEN
    -- Print a message to the console
    RAISE NOTICE 'Inserting data into employees table...';
    
    -- Insert data into employees table
    COPY employees FROM '/var/lib/postgresql/data/imports/employees.csv' DELIMITER ',' CSV HEADER;
    
    -- Print a message to the console
    RAISE NOTICE 'Data inserted into employees table.';
  END IF;
END $$;

-- Check if products table is empty
DO $$
BEGIN
  IF (SELECT COUNT(*) FROM products) = 0 THEN
    -- Print a message to the console
    RAISE NOTICE 'Inserting data into products table...';
    
    -- Insert data into products table
    COPY products FROM '/var/lib/postgresql/data/imports/products.csv' DELIMITER ',' CSV HEADER;
    
    -- Print a message to the console
    RAISE NOTICE 'Data inserted into products table.';
  END IF;
END $$;
