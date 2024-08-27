```mermaid
erDiagram
    user {
        id int
        email string
        password string
        is_active bool
        last_login timestamp
    }
    
    execution {
        id int
        user_id int
        pipe_id int
        status string "NOT_PROCESSED, ASSIGNED, FINISHED, ERROR"
        result string
        input string
        version int "1, 2"
        created_at timestamp
        started_at timestamp
        ended_at timestamp
    }
    
    pipe {
        id int
        user_id int
        name string
        is_public boolean
        created_at timestamp
        updated_at timestamp
    }
    
    execution_module {
        id int
        execution_id int
        module_name string
        args json
    }
    
    module {
        id int
        name text
        args json
    }
    
    pipe_module {
        id int
        pipe_id int
        module_id int
    }
    
    user ||--|{ pipe : ""
    pipe ||--|{ pipe_module : ""
    module ||--|{ pipe_module : ""
    execution ||--|{ execution_module: ""
    user ||--|{ execution: ""
    pipe ||--|{ execution: ""
    
```