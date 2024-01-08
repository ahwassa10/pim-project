/**
 * Holds the classes that implement the logical model of the program.
 *
 * The UserStore is a collection of Users. A User is a collection of
 * Groups. A Group is a collection of Photos. A Photo contains a
 * TagStore. A TagStore is a collection of tags.
 *
 * The model is completely separate from the code that implements the
 * visual aspects of the program. This decouples the model code from
 * the business code that specifies how the user visually interacts
 * with the model.
 */
package oldmodel;