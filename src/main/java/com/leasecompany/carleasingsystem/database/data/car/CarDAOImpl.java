package com.leasecompany.carleasingsystem.database.data.car;

import com.leasecompany.carleasingsystem.database.data.GenericDAOImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CarDAOImpl extends GenericDAOImpl<Car, Long> implements CarDAO {
    public CarDAOImpl(SessionFactory sessionFactory) {
        super(Car.class ,sessionFactory);
    }

    @Override
    public List<String> getUniqueStringValuesInColumn(String column) {
        List<String> uniqueValues;
        try (Session session = super.sessionFactory.openSession()) {
            HibernateCriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            JpaCriteriaQuery<String> query = criteriaBuilder.createQuery(String.class);
            JpaRoot<Car> root = query.from(Car.class);
            query.select(root.get(column));
            Stream<String> result = session.createQuery(query).getResultStream();
            uniqueValues = result.distinct().collect(Collectors.toList());
        }
        return uniqueValues;
    }

    @Override
    public byte[] convertToByteArray(File file) {
        byte[] bFile = new byte[(int) file.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            fileInputStream.close();
        } catch (FileNotFoundException fe) {
            System.err.println("Unable to find file");
            return null;
        } catch (IOException ie) {
            System.err.println("Unable to convert file to byte[]");
            return null;
        }

        return bFile;
    }
}