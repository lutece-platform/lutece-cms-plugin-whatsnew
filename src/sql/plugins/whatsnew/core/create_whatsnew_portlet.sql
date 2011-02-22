--
-- Structure for table whatsnew_portlet
--

DROP TABLE IF EXISTS whatsnew_portlet;
CREATE TABLE whatsnew_portlet (
  id_portlet int DEFAULT 0 NOT NULL,
  show_documents smallint DEFAULT 0 NOT NULL,
  show_portlets smallint DEFAULT 0 NOT NULL,
  show_pages smallint DEFAULT 0 NOT NULL,
  period int DEFAULT 0 NOT NULL,
  nb_elements_max int DEFAULT 0 NOT NULL,
  elements_order int DEFAULT 0 NOT NULL,
  is_asc_sort smallint DEFAULT 0 NOT NULL,
  is_dynamic smallint DEFAULT 1 NOT NULL
);