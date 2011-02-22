-- update table whatsnew_portlet
ALTER TABLE whatsnew_portlet ADD COLUMN is_asc_sort SMALLINT DEFAULT 0 NOT NULL;
ALTER TABLE whatsnew_portlet ADD COLUMN is_dynamic SMALLINT DEFAULT 1 NOT NULL;