package sv.edu.udb.www.practica_springmvc.controller;

import sv.edu.udb.www.practica_springmvc.entities.EditorialesEntity;
import sv.edu.udb.www.practica_springmvc.model.EditorialesModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
@Controller
@RequestMapping("editoriales")

public class EditorialesController {
    EditorialesModel editorialesModel = new EditorialesModel();
    @RequestMapping(value = "list", method = GET)

    public String listarEditoriales(ModelMap modelMap){
        //Pasando la lista de editoriales hacia la vista
        modelMap.addAttribute("listaEditoriales", editorialesModel.listarEditoriales());
        //Redireccionando a la página de editorailes.jsp
        return "editoriales/listar";
    }

    //Este método permite mostrar el formulario para el registro del editorial
    @RequestMapping(value = "create", method = GET)
    public String nuevoEditorial(Model model){
        model.addAttribute("editorial", new EditorialesEntity());
        return "editoriales/nuevo";
    }

    //Este método se ejecuta al enviar el formulario desde la vista
    @RequestMapping(value = "create", method = POST)
    public String insertarEditorial(@ModelAttribute("editorial") EditorialesEntity editorial, Model model, RedirectAttributes atributos) {
        if(editorialesModel.insertarEditorial(editorial)>0){
            atributos.addFlashAttribute("exito","Editorial registrado exitosamente");
            return "redirect:/editoriales/list";
        }
        else {
            model.addAttribute("editorial",editorial);
            return "editoriales/nuevo";
        }
    }

    @RequestMapping(value = "edit/{codigo}", method = GET)
    public String obtenerEditorial(@PathVariable("codigo") String codigo, Model model){
        model.addAttribute("editorial", editorialesModel.obtenerEditorial(codigo));
        return "editoriales/editar";
    }

    @RequestMapping(value = "edit/{codigo}", method = POST)
    public String modificarEditorial(EditorialesEntity editorial, Model model, RedirectAttributes atributos) {
        if(editorialesModel.modificarEditorial(editorial)>0){
            atributos.addFlashAttribute("exito","Editorial modificado exitosamente");
            return "redirect:/editoriales/list";
        }
        else {
            model.addAttribute("editorial",editorial);
            return "editoriales/editar";
        }
    }

    //El código del editorial a eliminar se recibe por la url
    @RequestMapping(value = "delete/{codigo}")
    public String eliminarEditorial(@PathVariable("codigo") String codigo, Model
            model, RedirectAttributes atributos){
        model.addAttribute("editorial", editorialesModel.obtenerEditorial(codigo));
        if(editorialesModel.eliminarEditorial(codigo)>0){
            atributos.addFlashAttribute("exito","Editorial eliminado exitosamente");
        }
        else {
            model.addAttribute("fracaso","no se puede eliminar este editorial");
        }
        return "redirect:/editoriales/list";
    }
}