package net.tarcadia.tribina.plugin.util.data;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;

public class TagTree<T> {

    private final String id;
    private final String path;
    private final int depth;
    private final TagTree<T> parent;
    private final TagTree<T> root;

    private final List<String> tags;
    private final Map<String, TagTree<T>> subTrees;
    private T value;

    public TagTree() {
        this.id = "root";
        this.path = "";
        this.depth = 0;
        this.parent = null;
        this.root = this;
        this.tags = new LinkedList<>();
        this.subTrees = new HashMap<>();
        this.value = null;

        this.tags.add("");
        this.subTrees.put("", this);
    }

    public TagTree(T value) {
        this.id = "root";
        this.path = "";
        this.depth = 0;
        this.parent = null;
        this.root = this;
        this.tags = new LinkedList<>();
        this.subTrees = new HashMap<>();
        this.value = value;

        this.tags.add("");
        this.subTrees.put("", this);
    }

    public TagTree(@NonNull TagTree<T> root, @NonNull TagTree<T> parent, @NonNull String tag, T value) {
        var split = tag.split("\\.", 2);
        this.id = split[0];
        this.path = parent.path + "." + this.id;
        this.depth = parent.depth + 1;
        this.parent = parent;
        this.root = root;
        this.tags = new LinkedList<>();
        this.subTrees = new HashMap<>();
        this.tags.add("");
        this.subTrees.put("", this);
        if (split.length == 2) {
            this.put(split[1], value);
        } else {
            this.put(value);
        }
    }

    /**
     * Returns a String {@code id} of the tree or branch node.
     *
     * <p>If the node is the root node of the tree, the {@code id} will be 'root'.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Returns a String {@code path} of the tree or branch node. The path will be organized as a sequence of id
     * separated by dot '.' from 'root' to the id of this node.
     */
    public String getPath() {
        return this.path;
    }

    /**
     * Returns an integer {@code depth} of the tree or branch node.
     *
     * <p>The depth of the root node is {@code 0}.
     */
    public int getDepth() {
        return this.depth;
    }

    /**
     * Returns a ref to the TagTree {@code parent} of the tree or branch node.
     *
     * <p>The parent of the root node is {@code null}.
     */
    public TagTree<T> getParent() {
        return this.parent;
    }

    /**
     * Returns a ref to the TagTree {@code root} of the tree or branch node.
     *
     * <p>The root of the root node is itself.
     */
    public TagTree<T> getRoot() {
        return this.root;
    }

    /**
     * Returns a list of String {@code tags} of the tree or branch node.
     *
     * <p>If {@code deep} is true, all tags of successors will be counted.
     *
     * <p>If {@code deep} is false, only tags of direct children will be counted.
     */
    public List<String> getTags(boolean deep) {
        if (!deep) {
            return List.copyOf(this.tags);
        } else {
            var deepTags = new LinkedList<String>();
            deepTags.add("");
            for (var branchId : this.tags) if (!branchId.equals("")) {
                for (var branchTag : this.subTrees.get(branchId).getTags(true)) {
                    deepTags.add(branchId + "." + branchTag);
                }
            }
            return deepTags;
        }
    }

    /**
     * Create and return a TagTree of the subtree or branch node identified by {@code tag} to the tree or branch node.
     *
     * <p>If a tag separated by '.' is provided, the whole sub-branch will be created, the specified sub-branch will be
     * returned.
     *
     * <p>If the branch exists, it will return the specified existing sub-branch.
     */
    public TagTree<T> createBranch(@NonNull String tag) {
        return Objects.requireNonNullElseGet(
                this.getBranch(tag),
                () -> {
                    this.put(tag, null);
                    return this.getBranch(tag);
                }
        );
    }

    /**
     * Returns a ref to the TagTree of the subtree or branch node identified by {@code tag}.
     *
     * <p>If a tag separated by '.' is provided, the specified sub-branch will be returned.
     *
     * <p>If there is not a branch identified by the tag, {@code null} will be returned.
     */
    public TagTree<T> getBranch(@NonNull String tag) {
        var split = tag.split("\\.", 2);
        var branchTree = this.subTrees.get(split[0]);
        if (branchTree != null && split.length == 2) {
            return branchTree.getBranch(split[1]);
        } else {
            return branchTree;
        }
    }

    /**
     * Returns and pop a ref to the TagTree of the subtree or branch node identified by {@code tag}. The branch will be
     * removed from the tree.
     *
     * <p>If a tag separated by '.' is provided, the specified sub-branch will be returned.
     *
     * <p>If there is not a branch identified by the tag, {@code null} will be returned.
     *
     * <p>The subtree or branch popped may still hold the origin tree as the root.
     */
    public TagTree<T> popBranch(@NonNull String tag){
        var split = tag.split("\\.", 2);
        var branchTree = this.subTrees.get(split[0]);
        if (branchTree != null && split.length == 2) {
            return branchTree.popBranch(split[1]);
        } else {
            this.tags.remove(split[0]);
            this.subTrees.remove(split[0]);
            return branchTree;
        }
    }

    /**
     * Put {@code value} at the tree or branch node.
     */
    public void put(T value) {
        this.value = value;
    }

    /**
     * Put {@code value} at the subtree or branch node identified by {@code tag}.
     *
     * <p>If a tag separated by '.' is provided, the {@code value} will be put to the specified sub-branch.
     *
     * <p>If there is not a branch identified by the tag, the branch will be created.
     */
    public void put(@NonNull String tag, T value) {
        var split = tag.split("\\.", 2);
        var branchTree = Objects.requireNonNullElseGet(
                this.subTrees.get(split[0]),
                () -> {
                    var subTree = new TagTree<>(this.root, this, split[0], null);
                    this.tags.add(split[0]);
                    this.subTrees.put(split[0], subTree);
                    return subTree;
                });
        if (split.length == 2) {
            branchTree.put(split[1], value);
        } else {
            branchTree.put(value);
        }
    }

    /**
     * Returns the {@code value} at the tree or branch node.
     */
    public T get() {
        return this.value;
    }

    /**
     * Returns the {@code value} at the subtree or branch node identified by {@code tag}.
     *
     * <p>If a tag separated by '.' is provided, the {@code value} of the specified sub-branch will be returned.
     *
     * <p>If there is not a branch identified by the tag, {@code null} will be returned.
     */
    public T get(@NonNull String tag) {
        var split = tag.split("\\.", 2);
        var branchTree = this.getBranch(split[0]);
        if (branchTree != null && split.length == 2) {
            return branchTree.get(split[1]);
        } else if (branchTree != null && split.length == 1) {
            return branchTree.get();
        } else {
            return null;
        }
    }

    /**
     * Returns {@code true} if this tree or branch node is the root node.
     */
    public boolean isRoot() {
        return (this.root == this);
    }

}
