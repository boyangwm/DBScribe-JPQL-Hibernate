CREATE TABLE `beanbook` (
  `barcode` varchar(20) NOT NULL,
  `bookname` varchar(200) NOT NULL,
  `pubid` varchar(20) DEFAULT NULL,
  `price` double NOT NULL,
  `state` varchar(20) NOT NULL DEFAULT '在库',
  PRIMARY KEY (`barcode`),
  KEY `fk_pubid_idx` (`pubid`),
  CONSTRAINT `fk_pubid` FOREIGN KEY (`pubid`) REFERENCES `beanpublisher` (`pubid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `beanbook` (
  `barcode` varchar(20) NOT NULL,
  `bookname` varchar(200) NOT NULL,
  `pubid` varchar(20) DEFAULT NULL,
  `price` double NOT NULL,
  `state` varchar(20) NOT NULL DEFAULT '在库',
  PRIMARY KEY (`barcode`),
  KEY `fk_pubid_idx` (`pubid`),
  CONSTRAINT `fk_pubid` FOREIGN KEY (`pubid`) REFERENCES `beanpublisher` (`pubid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `beanbooklendrecord` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `readerid` varchar(20) NOT NULL,
  `bookBarcode` varchar(20) NOT NULL,
  `lendDate` datetime NOT NULL,
  `returnDate` datetime DEFAULT NULL,
  `lendOperUserid` varchar(20) NOT NULL,
  `returnOperUserid` varchar(20) DEFAULT NULL,
  `penalSum` double DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_book_idx` (`bookBarcode`),
  KEY `fk_reader_idx` (`readerid`),
  KEY `fk_lendOper_idx` (`lendOperUserid`),
  KEY `fk_returnOper_idx` (`returnOperUserid`),
  CONSTRAINT `fk_book` FOREIGN KEY (`bookBarcode`) REFERENCES `beanbook` (`barcode`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_lendOper` FOREIGN KEY (`lendOperUserid`) REFERENCES `beansystemuser` (`userid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_reader` FOREIGN KEY (`readerid`) REFERENCES `beanreader` (`readerid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_returnOper` FOREIGN KEY (`returnOperUserid`) REFERENCES `beansystemuser` (`userid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

CREATE TABLE `beanpublisher` (
  `pubid` varchar(20) NOT NULL,
  `publisherName` varchar(50) NOT NULL,
  `address` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`pubid`),
  UNIQUE KEY `publisherName_UNIQUE` (`publisherName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `beanreader` (
  `readerid` varchar(20) NOT NULL,
  `readerName` varchar(50) NOT NULL,
  `readerTypeId` int(11) NOT NULL,
  `lendBookLimitted` int(11) NOT NULL,
  `createDate` datetime NOT NULL,
  `creatorUserId` varchar(20) NOT NULL,
  `removeDate` datetime DEFAULT NULL,
  `removerUserId` varchar(20) DEFAULT NULL,
  `stopDate` datetime DEFAULT NULL,
  `stopUserId` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`readerid`),
  KEY `fk_creator_idx` (`creatorUserId`),
  KEY `fk_remover_idx` (`removerUserId`),
  KEY `fk_stopper_idx` (`stopUserId`),
  CONSTRAINT `fk_creator` FOREIGN KEY (`creatorUserId`) REFERENCES `beansystemuser` (`userid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_remover` FOREIGN KEY (`removerUserId`) REFERENCES `beansystemuser` (`userid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_stopper` FOREIGN KEY (`stopUserId`) REFERENCES `beansystemuser` (`userid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `beanreadertype` (
  `readerTypeId` int(11) NOT NULL AUTO_INCREMENT,
  `readerTypeName` varchar(50) NOT NULL,
  `lendBookLimitted` int(11) NOT NULL,
  PRIMARY KEY (`readerTypeId`),
  UNIQUE KEY `readerTypeName_UNIQUE` (`readerTypeName`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

CREATE TABLE `beanreserve` (
  `reserveid` int(11) NOT NULL AUTO_INCREMENT,
  `readerid` varchar(45) NOT NULL,
  `bookBarcode` varchar(45) NOT NULL,
  `reserveDate` datetime NOT NULL,
  `cancelDate` datetime DEFAULT NULL,
  PRIMARY KEY (`reserveid`),
  KEY `fk_reader_idx` (`readerid`),
  KEY `fk_book_idx` (`bookBarcode`),
  CONSTRAINT `fk_reservebook` FOREIGN KEY (`bookBarcode`) REFERENCES `beanbook` (`barcode`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_reservereader` FOREIGN KEY (`readerid`) REFERENCES `beanreader` (`readerid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

CREATE TABLE `beansystemuser` (
  `userid` varchar(20) NOT NULL,
  `username` varchar(50) NOT NULL,
  `pwd` varchar(32) NOT NULL,
  `usertype` varchar(20) NOT NULL,
  `createDate` datetime NOT NULL,
  `removeDate` datetime DEFAULT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
