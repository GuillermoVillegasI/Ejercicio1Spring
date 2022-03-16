package com.libreria.servicios;

import com.libreria.entidades.Autor;
import com.libreria.errores.ErrorServicio;
import com.libreria.repositorios.AutorRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;
    
    @Transactional(rollbackFor = Exception.class)
    public void crear(String nombre, boolean alta) throws ErrorServicio {

        Autor autor = new Autor();

        validar(nombre);

        autor.setNombre(nombre);
        autor.setAlta(alta);

        autorRepositorio.save(autor);

    }

    @Transactional(readOnly = true) // SOLO LEER EN BASE DE DATOS ------ No DEBERIA MODIFICAR NADA EN LA BASE , PERO SI POR ALGUN ERROR TIENE QUE MODIFICAR , TIRA ERROR Y NOS AVISA
    public Autor buscarPorId(String id) throws ErrorServicio {

        Optional<Autor> respuesta = autorRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Autor autor = respuesta.get();
            return autor;

        } else {

            throw new ErrorServicio("NO EXISTE ESE AUTOR");
        }

    }
    @Transactional(rollbackFor = Exception.class)
    public void consultar(String id) throws ErrorServicio {

        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Autor autor = respuesta.get();
            System.out.println(autor);

            autorRepositorio.save(autor);

        } else {

            throw new ErrorServicio(" No se encontro la Autor ");

        }

    }
    @Transactional(rollbackFor = Exception.class)
    public void modificar(String id, String nombre, boolean alta) throws ErrorServicio {

        validar(nombre);

        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Autor autor = respuesta.get();
            autor.setNombre(nombre);
            autor.setAlta(alta);

            autorRepositorio.save(autor);

        } else {

            throw new ErrorServicio(" No se encontro la Autor ");

        }
    }
    public void darDeBaja(String id) throws ErrorServicio {

        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Autor autor = respuesta.get();
            autor.setAlta(false);

            autorRepositorio.save(autor);

        } else {

            throw new ErrorServicio(" No se encontro la Autor ");

        }

    }
    @Transactional(rollbackFor = Exception.class)
    public void elimninar(String id) throws ErrorServicio {

        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Autor autor = respuesta.get();
            autorRepositorio.deleteById(id);

            autorRepositorio.save(autor);

        } else {

            throw new ErrorServicio(" No se encontro la Autor ");

        }
    }

    private void validar(String nombre) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {

            throw new ErrorServicio(" El nombre de la Autor no puede ser Nulo. ");

        }

    }

}
