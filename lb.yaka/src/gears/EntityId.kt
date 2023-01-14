package lb.yaka.gears

class EntityId {

    val parent: EntityId?
    val type: String
    val name: String?
    val alias: String?


    constructor(type: String, name: String?) {
        this.parent = null
        this.type = type
        this.name = name
        this.alias = null
    }

    constructor(type: String, name: String?, alias: String?) {
        this.parent = null
        this.type = type
        this.name = name
        this.alias = alias
    }

    private constructor(parent: EntityId?, type: String, name: String?, alias: String? = null) {
        this.parent = parent
        this.type = type
        this.name = name
        this.alias = alias
    }


    fun aka(alias: String): EntityId =
        EntityId(parent = this.parent, type = this.type, name = this.name, alias = alias)

    fun inner(marker: PropertyMarker, alias: String? = null): EntityId =
        EntityId(this, "property", marker.propertyName, alias)
    
    fun inner(type: String, name: String, alias: String? = null): EntityId =
        EntityId(this, type, name, alias)


    val simpleName: String
        get() {
            val displayName = alias ?: name
            return if (displayName != null) "$type $displayName" else type
        }

    val fullName: String
        get() = if (parent == null) simpleName
                else simpleName + " of " + parent.fullName

    fun nameInContext(contextId: EntityId?): String {
        if (parent == null) return simpleName
        if (contextId == null) return fullName

        val b = StringBuilder()
        b.append(this.simpleName)
        var id: EntityId? = this.parent
        while (id != null && id !== contextId) {
            b.append(" of ").append(id.simpleName)
            id = id.parent
        }
        return b.toString()
    }

    override fun toString(): String = fullName

}