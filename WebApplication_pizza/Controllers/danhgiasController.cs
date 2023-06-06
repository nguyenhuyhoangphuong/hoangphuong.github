using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using WebApplication_pizza.Models;

namespace WebApplication_pizza.Controllers
{
    public class danhgiasController : ApiController
    {
        private qlpizzaEntities14 db = new qlpizzaEntities14();

        // GET: api/danhgias
        public IQueryable<danhgia> Getdanhgias()
        {
            return db.danhgias;
        }

        // GET: api/danhgias/5
        [ResponseType(typeof(danhgia))]
        public IHttpActionResult Getdanhgia(string id)
        {
            danhgia danhgia = db.danhgias.Find(id);
            if (danhgia == null)
            {
                return NotFound();
            }

            return Ok(danhgia);
        }

        // PUT: api/danhgias/5
        [ResponseType(typeof(void))]
        public IHttpActionResult Putdanhgia(string id, danhgia danhgia)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != danhgia.masp)
            {
                return BadRequest();
            }

            db.Entry(danhgia).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!danhgiaExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return StatusCode(HttpStatusCode.NoContent);
        }

        // POST: api/danhgias
        [ResponseType(typeof(danhgia))]
        public IHttpActionResult Postdanhgia(danhgia danhgia)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.danhgias.Add(danhgia);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateException)
            {
                if (danhgiaExists(danhgia.masp))
                {
                    return Conflict();
                }
                else
                {
                    throw;
                }
            }

            return CreatedAtRoute("DefaultApi", new { id = danhgia.masp }, danhgia);
        }

        // DELETE: api/danhgias/5
        [ResponseType(typeof(danhgia))]
        public IHttpActionResult Deletedanhgia(string id)
        {
            danhgia danhgia = db.danhgias.Find(id);
            if (danhgia == null)
            {
                return NotFound();
            }

            db.danhgias.Remove(danhgia);
            db.SaveChanges();

            return Ok(danhgia);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool danhgiaExists(string id)
        {
            return db.danhgias.Count(e => e.masp == id) > 0;
        }
    }
}