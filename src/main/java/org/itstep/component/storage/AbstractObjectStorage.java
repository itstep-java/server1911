package org.itstep.component.storage;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

abstract class AbstractObjectStorage<T> {
    private class AppendingObjectOutputStream extends ObjectOutputStream {
        AppendingObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        @Override
        protected void writeStreamHeader() throws IOException {
            reset();
        }
    }

    private class ObjectFileReader<E> {
        @SuppressWarnings("unchecked")
        E read(Predicate<E> predicate) throws IOException, ClassNotFoundException {
            E result = null;
                try (ObjectInputStream inputStream = getInputStream()) {

                    while (true) {
                        E object = (E) inputStream.readObject();

                        if (predicate.test(object)) {
                            result =  object;
                        }
                    }
                } catch (EOFException e) {
                }

            return result;
        }

        @SuppressWarnings("unchecked")
        ArrayList<E> readAll() throws IOException, ClassNotFoundException {
            ArrayList<E> objects = new ArrayList<>();

            try (ObjectInputStream inputStream = getInputStream()) {
                while (true) {
                    objects.add((E) inputStream.readObject());
                }
            } catch (EOFException e) {
            }

            return objects;
        }

        private ObjectInputStream getInputStream() throws IOException {
            return new ObjectInputStream(new FileInputStream(file));
        }
    }

    private class ObjectFileWriter<E> {
        void write(E object, boolean appending) throws IOException {
            try (ObjectOutputStream outputStream = getOutputStream(appending)) {
                outputStream.writeObject(object);
            }
        }

        void write(E object) throws IOException {
            write(object, false);
        }

        void write(Collection<E> objects) throws IOException {
            try (ObjectOutputStream outputStream = getOutputStream()) {
                for (E object : objects) {
                    outputStream.writeObject(object);
                }
            }
        }

        private ObjectOutputStream getOutputStream(boolean appending) throws IOException {
            FileOutputStream fileOutputStream = new FileOutputStream(file, appending);

            return file.length() > 0
                ? new AppendingObjectOutputStream(fileOutputStream)
                : new ObjectOutputStream(fileOutputStream);
        }

        private ObjectOutputStream getOutputStream() throws IOException {
            return getOutputStream(false);
        }
    }

    private File file;

    private ObjectFileReader<T> reader = new ObjectFileReader<>();

    private ObjectFileWriter<T> writer = new ObjectFileWriter<>();

    AbstractObjectStorage(File file) {
        this.file = file;
    }

    T read(Predicate<T> predicate) throws IOException, ClassNotFoundException {
        return reader.read(predicate);
    }

    ArrayList<T> readAll() throws IOException, ClassNotFoundException {
        return reader.readAll();
    }

    void write(T object, boolean appending) throws IOException {
        writer.write(object, appending);
    }

    void write(T object) throws IOException {
        writer.write(object);
    }

    void write(Collection<T> objects) throws IOException {
        writer.write(objects);
    }
}
