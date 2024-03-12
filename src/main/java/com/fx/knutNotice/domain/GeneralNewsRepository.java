package com.fx.knutNotice.domain;

import com.fx.knutNotice.domain.entity.GeneralNews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneralNewsRepository extends JpaRepository<GeneralNews, Long> {


/*
    private final Map<Long, GeneralNews> store = new ConcurrentHashMap<>();

    public void save(GeneralNews generalNews) {
        store.put(generalNews.getNttId(), generalNews);
    }

    public void findByNttId(Long nttId) {
        store.get(nttId);
    }

    public List<GeneralNews> findAll() {
        return new ArrayList<>(store.values());
    }

    public void deleteBoard(Long nttId) {
        if (store.containsKey(nttId)) {
            store.remove(nttId);
        }
    }
 */
}
